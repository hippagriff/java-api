package com.hippagriff.security;

import static org.apache.commons.lang.StringUtils.isBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.hippagriff.model.User;
import com.hippagriff.patients.dao.UserDAO;

/**
 * Authentication mechanism for authenticating users against the {@link User} table in the DB.
 * 
 * @author jon
 */
public class DBAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    public DBAuthenticationProvider()
    {
        // no arg consturctor
    }

    public DBAuthenticationProvider(UserDAO userDAO, PasswordEncryptor passwordEncryptor)
    {
        this.userDAO = userDAO;
        this.passwordEncryptor = passwordEncryptor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.
     * core.Authentication) Provides custom authentication method
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String userName = String.valueOf(auth.getPrincipal());
        String userPassword = String.valueOf(auth.getCredentials());

        if (isBlank(userName))
        {
            throw new BadCredentialsException("Username not provided.");
        }

        if (isBlank(userPassword))
        {
            throw new BadCredentialsException("Password not provided.");
        }

        User user = userDAO.findByUserName(userName);
        if (user == null)
        {
            throw new BadCredentialsException("Invalid credentials provided.");
        }

        boolean passwordsMatch = passwordEncryptor.isPasswordsEqual(user.getEncryptedPassword(), userPassword);

        if (!passwordsMatch)
        {
            throw new BadCredentialsException("Password incorrect.");
        }

        APIUser apiUser = new APIUser(user);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                apiUser, null, apiUser.getAuthorities());

        return usernamePasswordAuthenticationToken;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<? extends Object> authentication)
    {
        // copied it from AbstractUserDetailsAuthenticationProvider
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
