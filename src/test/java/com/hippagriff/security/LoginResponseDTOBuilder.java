package com.hippagriff.security;

import com.hippagriff.dto.LoginResponseDTO;

/**
 * Builder class for {@link LoginResponseDTO}
 * 
 * @author jon
 */
public class LoginResponseDTOBuilder
{
    private LoginResponseDTO loginResponseDTO;

    String userId;

    String personId;

    String firstName;

    String lastName;

    String userName;

    String userPassword;

    Boolean forcePasswordReset;

    String authenticationToken;

    public static LoginResponseDTOBuilder aBasicLoginResponseDTO()
    {
        LoginResponseDTOBuilder builder = new LoginResponseDTOBuilder();
        
        builder.authenticationToken = "authToken123";
        builder.userId = "userId123";
        builder.personId = "pers123";
        builder.firstName = "firstName";
        builder.lastName = "LastName";
        builder.forcePasswordReset = true;

        return builder;
    }

    public LoginResponseDTO build()
    {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setAuthenticationToken(authenticationToken);
        loginResponseDTO.setFirstName(firstName);
        loginResponseDTO.setLastName(lastName);
        loginResponseDTO.setPersonId(personId);
        loginResponseDTO.setUserId(userId);
        loginResponseDTO.setUserName(userName);
        loginResponseDTO.setForcePasswordReset(forcePasswordReset);
        
        return this.loginResponseDTO;
    }

}
