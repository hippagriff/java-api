package com.hippagriff.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hippagriff.security.PasswordEncryptor;

/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@Table(name = "User")
public class User extends com.hippagriff.model.BaseModel implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String userId;

    private Person primaryPerson;

    private String userName;

    private String encryptedPassword;

    private int userStatus;

    private Boolean forcePasswordReset;

    public User()
    {
        // empty constructor
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    // bi-directional one-to-one association to Person
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "primary_person_id")
    public Person getPrimaryPerson()
    {
        return primaryPerson;
    }

    public void setPrimaryPerson(Person primaryPerson)
    {
        this.primaryPerson = primaryPerson;
    }

    @Column(name = "user_name")
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Lob()
    @Column(name = "password")
    public String getEncryptedPassword()
    {
        return encryptedPassword;
    }

    @Transient
    public void setPassword(String password)
    {
        setEncryptedPassword(PasswordEncryptor.encryptPassword(password));
    }

    public void setEncryptedPassword(String encryptedPassword)
    {
        this.encryptedPassword = encryptedPassword;
    }

    @Column(name = "user_status")
    public int getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus(int userStatus)
    {
        this.userStatus = userStatus;
    }

    @Column(name = "force_pw_reset")
    public Boolean getForcePasswordReset()
    {
        return forcePasswordReset;
    }

    public void setForcePasswordReset(Boolean forcePasswordReset)
    {
        this.forcePasswordReset = forcePasswordReset;
    }

}