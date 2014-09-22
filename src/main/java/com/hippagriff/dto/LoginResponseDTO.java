package com.hippagriff.dto;

/**
 * This DTO encapsulates all of the information required after a user succesfully authenticates and is logged in.
 * 
 * @author jon
 * 
 */
public class LoginResponseDTO
{
    private String userId;

    private String personId;

    private String firstName;

    private String lastName;

    private String userName;

    private String userPassword;

    private Boolean forcePasswordReset;

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

}
