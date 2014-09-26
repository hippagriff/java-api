package com.hippagriff.dto;

import java.io.Serializable;

/**
 * This DTO encapsulates all of the information required after a user successfully authenticates and is logged in.
 * 
 * @author jon
 * 
 */
public class LoginResponseDTO implements Serializable
{
    private static final long serialVersionUID = -6079887002357692650L;

    private String userId;

    private String personId;

    private String firstName;

    private String lastName;

    private String userName;

    private String userPassword;

    private Boolean forcePasswordReset;

    private String authenticationToken;

    public LoginResponseDTO()
    {
        // do nothing here
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserPassword()
    {
        return userPassword;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

    public Boolean getForcePasswordReset()
    {
        return forcePasswordReset;
    }

    public void setForcePasswordReset(Boolean forcePasswordReset)
    {
        this.forcePasswordReset = forcePasswordReset;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getPersonId()
    {
        return personId;
    }

    public void setPersonId(String personId)
    {
        this.personId = personId;
    }

    public String getAuthenticationToken()
    {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken)
    {
        this.authenticationToken = authenticationToken;
    }
}
