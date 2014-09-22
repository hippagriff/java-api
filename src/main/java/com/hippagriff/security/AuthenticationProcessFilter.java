package com.hippagriff.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * Provides authentication mechanism that validates a passed in client token in the header of each request.
 * 
 * @author jon
 */
public class AuthenticationProcessFilter extends UsernamePasswordAuthenticationFilter implements LogoutHandler
{
    @Autowired
    private LDAPAuthenticationProvider ldapAuthentication;

    public static String USERNAME_PARAMETER = "username";
    public static String PASSWORD_PARAMETER = "password";

    public AuthenticationProcessFilter()
    {
        // no arg-constructor
    }

    /**
     * Constructor used for unit testing.
     * 
     * @param ldapAuthentication
     */
    public AuthenticationProcessFilter(LDAPAuthenticationProvider ldapAuthentication,
            AuthenticationManager authenticationManager)
    {
        this.ldapAuthentication = ldapAuthentication;
        this.setAuthenticationManager(authenticationManager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain) Check whether the authentication token passed in as a uri request parameter is valid.
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException
    {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        logger.debug("Attempting authentication.");

        Authentication authResult;
        try
        {
            authResult = attemptAuthentication(request, response);
            if (authResult == null)
            {
                // return immediately as subclass has indicated that it hasn't completed authentication
                return;
            }

            logger.debug("Authentication succesful");

            // Set the authentication object in context for validating authorizations
            SecurityContextHolder.getContext().setAuthentication(authResult);
        }
        catch (AuthenticationException failed)
        {
            // Authentication failed
            unsuccessfulAuthentication(request, response, failed);
            return;
        }

        // Continue further processing of the spring filter chain...
        chain.doFilter(request, response);

        return;
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

    /**
     * @return the ldapAuthentication
     */
    public LDAPAuthenticationProvider getLDAPAuthentication()
    {
        return ldapAuthentication;
    }

    /**
     * @param ldapAuthentication the ldapAuthentication to set
     */
    public void setLDAPAuthentication(LDAPAuthenticationProvider ldapAuthentication)
    {
        this.ldapAuthentication = ldapAuthentication;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    {
        // No logout implementation for this environment.
        return;
    }
}