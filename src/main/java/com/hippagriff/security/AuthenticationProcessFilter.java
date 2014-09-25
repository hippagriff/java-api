package com.hippagriff.security;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.hippagriff.cache.CacheInterface;

/**
 * Provides authentication mechanism that validates a passed in client token in the header of each request.
 * 
 * @author jon
 */
@Component
public class AuthenticationProcessFilter extends UsernamePasswordAuthenticationFilter implements LogoutHandler
{
    @Autowired
    private CacheInterface cacheImpl;

    @Autowired
    private AuthenticationPostProcessor authenticationPostProcessor;

    @Autowired
    private AuthenticationBusinessService authenticationBusinessService;

    public static String USERNAME_PARAMETER = "username";
    public static String PASSWORD_PARAMETER = "password";

    public AuthenticationProcessFilter()
    {
        // no arg-constructor
    }

    /**
     * Constructor used for unit testing.
     * 
     * @param authenticationManager
     * @param authenticationBusinessService
     * @param authenticationPostProcessor
     * @param cacheImpl
     */
    public AuthenticationProcessFilter(AuthenticationManager authenticationManager,
            AuthenticationBusinessService authenticationBusinessService,
            AuthenticationPostProcessor authenticationPostProcessor, CacheInterface cacheImpl)
    {
        this.setAuthenticationManager(authenticationManager);
        this.authenticationBusinessService = authenticationBusinessService;
        this.authenticationPostProcessor = authenticationPostProcessor;
        this.cacheImpl = cacheImpl;
    }

    /**
     * This method is responsible for determining what kind of authentication needs to be done. <br/>
     * <ul>
     * <li>
     * The first scenario is a basic Login; where the user hits a specific login service with credentials present in the
     * request headers to be authenticated. This service returns an authentication token that can be used to access the
     * app further.</li>
     * <li>
     * The second scenario is when a user is attempting to hit a service directly (non-login) with the credentials
     * specified in the request header. These credentials are authenticated, and then the user is passed onto the
     * service they requested.</li>
     * <li>
     * The third scenario, is when the user passes a authentication token with their request as a query parameter. This
     * token is fetched out of our cache and is validated to ensure this session still exists. Assuming the token is
     * valid, the user is passed onto the service they requested.</li>
     * </ul>
     * <br/>
     * Should any of these scenarios fail due to invalid credentials/token we return an HTTP Status 401.
     * 
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException
    {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        logger.debug("Attempting authentication.");

        Authentication authResult = null;
        try
        {
            if (requiresAuthentication(request, response))
            {

                authResult = attemptAuthentication(request, response);
                if (authResult == null)
                {
                    // return immediately as subclass has indicated that it hasn't completed authentication
                    return;
                }

                successfulAuthentication(request, response, authResult);

                return;
            }
            else if (checkHasUserNamePasswordHeaders(request))
            {
                logger.debug("Request is to process authentication for interface access");
                try
                {
                    authResult = attemptAuthentication(request, response);
                    if (authResult == null)
                    {
                        // return immediately as subclass has indicated that it hasn't completed authentication
                        return;
                    }
                }
                catch (AuthenticationException failed)
                {
                    // Authentication failed
                    unsuccessfulAuthentication(request, response, failed);
                    return;
                }

                successfulAuthentication(request, response, authResult);

                // Continue further processing of the spring filter chain...
                chain.doFilter(request, response);

                return;
            }

            // Set the authentication object in context for validating authorizations
            SecurityContextHolder.getContext().setAuthentication(authResult);
        }
        catch (AuthenticationException failed)
        {
            // Authentication failed
            unsuccessfulAuthentication(request, response, failed);
            return;
        }

        validateAuthenticationToken(request, response);

        // Continue further processing of the spring filter chain...
        chain.doFilter(request, response);

        return;
    }

    /**
     * Extract the authenticationToken from the incoming {@link HttpServletRequest}, and validate that it exists in the
     * cache.
     * 
     * @param request
     * @param response
     */
    protected void validateAuthenticationToken(HttpServletRequest request, HttpServletResponse response)
    {
        // Get the token value by decoding the authToken value passed in request if present
        String tokenValue = authenticationBusinessService.extractAuthToken(request);
        if (isNotBlank(tokenValue))
        {
            // Get the cached authentication object corresponding to the authToken
            Authentication authentication = cacheImpl.get(tokenValue);

            APIUser apiUser = null;

            if (authentication != null)
            {
                apiUser = (APIUser) authentication.getPrincipal();
            }

            // If the request token is still valid then continue with further processing of the request
            if (isAuthTokenValid(apiUser))
            {
                // Set the authentication object in context for validating authorizations
                authenticationBusinessService.setAuthenticationIntoContext(authentication);
                // Update the expiry time to start from now
                apiUser.setAuthenticationTokenExpiryDate(new Date());
                // Update the authentication object in cache
                cacheImpl.replace(tokenValue, authentication);
            }
            // If the client is valid and the authToken has expired delete the auth object from cache
            else
            {
                // remove the stale authentication object from cache and invalidate the token
                cacheImpl.remove(tokenValue);
                authenticationBusinessService.removeAuthenticationTokenFromUsersList(apiUser, tokenValue);

                // If the authToken is in-valid
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    /**
     * Checks if this token is still valid for use based on a preset duration.
     * 
     * @return true/false
     */
    public boolean isAuthTokenValid(APIUser apiUser)
    {
        if (apiUser == null)
        {
            return false;
        }
        Boolean returnVal = true;
        Long expireTime = (apiUser.getAuthenticationTokenExpiryDate().getTime() + AuthenticationBusinessService.AUTH_TOKEN_CACHE_MILLISECONDS);
        Long currentTime = System.currentTimeMillis();
        if (currentTime > expireTime)
        {
            returnVal = false;
        }
        return returnVal;
    }

    /**
     * Extract username value from Request Header params
     */
    protected String obtainUsername(HttpServletRequest request)
    {
        String token = null;

        @SuppressWarnings("unchecked")
        Enumeration<String> tokenHeader = request.getHeaders(USERNAME_PARAMETER);
        if (tokenHeader.hasMoreElements())
        {
            token = tokenHeader.nextElement();
        }
        return token;
    }

    /**
     * Extract password value from Request header params
     */
    protected String obtainPassword(HttpServletRequest request)
    {
        String token = null;

        @SuppressWarnings("unchecked")
        Enumeration<String> tokenHeader = request.getHeaders(PASSWORD_PARAMETER);
        if (tokenHeader.hasMoreElements())
        {
            token = tokenHeader.nextElement();
        }
        return token;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter#unsuccessfulAuthentication
     * (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.AuthenticationException)
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException
    {
        logger.debug("Unsuccessful authentication");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException
    {
        String userName = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);

        setDetails(request, authRequest);

        return getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * @param request
     * @return true if values for both 'username' and 'password' headers have been passed in from the request.
     */
    public boolean checkHasUserNamePasswordHeaders(HttpServletRequest request)
    {
        String passwordHash = request.getHeader(getPasswordParameter());
        String userNameHeader = request.getHeader(getUsernameParameter());
        if (isNotBlank(passwordHash) && isNotBlank(userNameHeader))
        {
            return true;
        }
        return false;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            Authentication authResult) throws ServletException, IOException
    {
        authenticationPostProcessor.postProcess(response, authResult, true);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    {
        return;
    }
}