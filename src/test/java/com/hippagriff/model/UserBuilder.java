package com.hippagriff.model;

/**
 * Builder class for {@link User}
 * 
 * @author jon
 */
public class UserBuilder
{
    String userId;
    Person primaryPerson;
    String userName;
    String userPassword;
    Boolean forcePasswordReset;

    public static UserBuilder aBasicUser()
    {
        UserBuilder builder = new UserBuilder();

        builder.userId = "userId123";
        builder.primaryPerson = PersonBuilder.aBasicPerson().build();
        builder.forcePasswordReset = true;

        return builder;
    }

    public User build()
    {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setPrimaryPerson(primaryPerson);

        return user;
    }

}
