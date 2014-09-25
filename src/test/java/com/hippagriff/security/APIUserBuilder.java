package com.hippagriff.security;

import java.util.Date;

import com.hippagriff.dto.LoginResponseDTO;

/**
 * Builder class for {@link APIUser}
 * 
 * @author jon
 */
public class APIUserBuilder
{

    String userId;

    String personId;

    String firstName;

    String lastName;

    String userName;

    String userPassword;

    Boolean forcePasswordReset;

    String authenticationToken;

    Date tokenExpiryDate;

    LoginResponseDTO loginResponseDTO;

    public static APIUserBuilder aBasicAPIUser()
    {
        APIUserBuilder builder = new APIUserBuilder();

        builder.authenticationToken = "authToken123";
        builder.userId = "userId123";
        builder.personId = "pers123";
        builder.firstName = "firstName";
        builder.lastName = "LastName";
        builder.forcePasswordReset = true;
        builder.tokenExpiryDate = new Date();
        builder.loginResponseDTO = LoginResponseDTOBuilder.aBasicLoginResponseDTO().build();

        return builder;
    }
    
    public APIUserBuilder withEmptyAuthToken()
    {
        this.authenticationToken = null;
        this.tokenExpiryDate = null;
        return this;
    }

    public APIUser build()
    {

        APIUser user = new APIUser();
        user.setAuthenticationToken(authenticationToken);
        user.setAuthenticationTokenExpiryDate(tokenExpiryDate);
        user.setFirstName(firstName);
        user.setUserId(userId);
        user.setUserName(userName);
        user.setLoginDetails(loginResponseDTO);
        user.setPrimaryPersonId(personId);

        return user;
    }

}
