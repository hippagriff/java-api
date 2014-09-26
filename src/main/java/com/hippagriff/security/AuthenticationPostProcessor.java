package com.hippagriff.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.hippagriff.dto.LoginResponseDTO;
import com.hippagriff.model.AuthenticationException;
import com.hippagriff.model.Person;
import com.hippagriff.model.User;
import com.hippagriff.patients.dao.UserDAO;

/**
 * Performs application specific processing after successful authentication. For instance, auditing, updating cache,
 * sending {@link LoginResponseDTO} to the requester
 * 
 * @author jon
 * 
 */
@Component
public class AuthenticationPostProcessor
{
    private final Logger logger = LoggerFactory.getLogger(AuthenticationPostProcessor.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AuthenticationBusinessService authenticationBusinessService;

    public AuthenticationPostProcessor()
    {
        // no-arg empty constructor
    }

    public AuthenticationPostProcessor(UserDAO userDAO, AuthenticationBusinessService authenticationBusinessService)
    {
        this.userDAO = userDAO;
        this.authenticationBusinessService = authenticationBusinessService;
    }

    /**
     * Performs additional processing after successful authentication. For instance, auditing, updating cache, sending
     * {@link LoginResponseDTO} to the requester
     * 
     * @param response
     * @param authentication
     * @param returnResponse - true if {@link LoginResponseDTO} should be sent after this is processed.
     */
    public void postProcess(HttpServletResponse response, Authentication authentication, boolean returnResponse)
    {
        logger.debug("Successful authentication");

        APIUser apiUser = (APIUser) authentication.getPrincipal();

        // Create a new authentication token for this login request
        apiUser.setAuthenticationToken(authenticationBusinessService.generateTokenData());

        authenticationBusinessService.setAuthenticationIntoContext(authentication);

        cacheUserAuthenticationTokens(apiUser);

        LoginResponseDTO loginResponseDTO = getLoginResponseDTO(apiUser);

        if (returnResponse)
        {
            buildLoginResponse(response, loginResponseDTO);
        }

        apiUser.setLoginDetails(loginResponseDTO);
        apiUser.setAuthenticationTokenExpiryDate(new Date());

        cacheUserAuthenticationObject(apiUser, authentication);
    }

    /**
     * Build a {@link LoginResponseDTO} that will be returned in the {@link HttpServletResponse} to the user.
     * 
     * @param response
     * @param loginResponseDTO
     */
    protected void buildLoginResponse(HttpServletResponse response, LoginResponseDTO loginResponseDTO)
    {
        PrintWriter responseWriter;
        response.setContentType(MediaType.APPLICATION_JSON);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        try
        {
            responseWriter = response.getWriter();
            responseWriter.append(new ObjectMapper().writeValueAsString(loginResponseDTO));
            authenticationBusinessService.addToCache(loginResponseDTO.getUserId()
                    + AuthenticationBusinessService.LOGIN_DETAILS_CACHE_KEY, loginResponseDTO);
            responseWriter.flush();
        }
        catch (IOException e)
        {
            String errorMessage = "Unable to write Login Response DTO for user: " + loginResponseDTO.getUserName();
            logger.error(errorMessage);
            throw new AuthenticationException(errorMessage);
        }
    }

    /**
     * Cache the authentication object and authentication token.
     * 
     * @param apiUser
     * @param authentication
     */
    private void cacheUserAuthenticationObject(APIUser apiUser, Authentication authentication)
    {
        authenticationBusinessService.addToCacheWithTTL(apiUser.getAuthenticationToken(), authentication,
                AuthenticationBusinessService.AUTH_TOKEN_CACHE_SECONDS);

        // TODO: Determine why we need to keep track of all cached authTokens?
        authenticationBusinessService.addToCachedAuthTokenList(apiUser.getAuthenticationToken());
    }

    /**
     * Cache the authentication tokens of the user by userId.
     * 
     * @param apiUser
     */
    @SuppressWarnings("unchecked")
    private void cacheUserAuthenticationTokens(APIUser apiUser)
    {
        // Fetch the secondary key to get all the current authentications for the logged in principal
        String userId = apiUser.getUserId();
        
        List<String> userAuthentications = (List<String>) authenticationBusinessService.getFromCache(userId);

        boolean newUserList = false;
        if (CollectionUtils.isEmpty(userAuthentications))
        {
            userAuthentications = new ArrayList<String>();
            newUserList = true;
        }

        if (!userAuthentications.contains(apiUser.getAuthenticationToken()))
        {
            userAuthentications.add(apiUser.getAuthenticationToken());
        }
        if (newUserList)
        {
            authenticationBusinessService.addToCache(userId, userAuthentications);
        }
        else
        {
            authenticationBusinessService.replaceInCache(userId, userAuthentications);
        }
    }

    /**
     * Constructs a {@link LoginResponseDTO} based on the provided {@link APIUser}
     * 
     * @param apiUser
     * @return
     */
    protected LoginResponseDTO getLoginResponseDTO(APIUser apiUser)
    {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        User user = userDAO.findActive(apiUser.getUserId());
        if (user == null)
        {
            throw new BadCredentialsException("User could not be found!");
        }

        String encodedTokenString = authenticationBusinessService.encodeAuthToken(apiUser.getAuthenticationToken());
        
        loginResponseDTO.setUserName(user.getUserName());
        loginResponseDTO.setUserId(user.getUserId());
        loginResponseDTO.setForcePasswordReset(user.getForcePasswordReset());
        loginResponseDTO.setAuthenticationToken(encodedTokenString);
        if (user.getPrimaryPerson() != null)
        {
            Person primaryPerson = user.getPrimaryPerson();
            loginResponseDTO.setPersonId(primaryPerson.getPersonId());
            loginResponseDTO.setFirstName(primaryPerson.getFirstName());
            loginResponseDTO.setLastName(primaryPerson.getLastName());
        }
        return loginResponseDTO;
    }
}
