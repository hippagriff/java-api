package com.hippagriff.model;

/**
 * Builder class for {@link Person}
 * 
 * @author jon
 */
public class PersonBuilder
{

    private String personId;
    private String userId;
    private String firstName;
    private String lastName;

    public static PersonBuilder aBasicPerson()
    {
        PersonBuilder builder = new PersonBuilder();

        builder.personId = "pers123";
        builder.firstName = "firstName";
        builder.lastName = "LastName";
        builder.userId = "userId123";

        return builder;
    }

    public Person build()
    {

        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setPersonId(personId);
        User user = new User();
        user.setUserId(userId);
        person.setUser(user);

        return person;
    }

}
