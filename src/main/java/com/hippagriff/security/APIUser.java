package com.hippagriff.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hippagriff.dto.LoginResponseDTO;
import com.hippagriff.dto.UserDTO;
import com.hippagriff.model.Person;
import com.hippagriff.model.User;

/**
 * Customized Spring UserDetails implementation.
 * 
 * @author jon
 */
public class APIUser implements UserDetails
{

    private static final long serialVersionUID = 1L;

    private static final String ROLE_API_APPLICATION_USER = "ROLE_API_APPLICATION_USER";

    private String userId;
    private String primaryPersonId;
    private String userName;
    private String firstName;
    private String lastName;
    private String authenticationToken;
    private Date authenticationTokenExpiryDate;
    private LoginResponseDTO loginDetails;
    
    public APIUser()
    {
        // do nothing
    }

    public APIUser(UserDTO userDTO)
    {
        if (userDTO != null)
        {
            setUserId(userDTO.getUserId());
            setUserName(userDTO.getUserName());
            setFirstName(userDTO.getFirstName());
            setLastName(userDTO.getLastName());
        }
    }
    

    public APIUser(User user)
    {
        if (user != null)
        {
            setUserId(user.getUserId());
            setUserName(user.getUserName());
            if(user.getPrimaryPerson() != null)
            {
                Person person = user.getPrimaryPerson();
                setPrimaryPersonId(person.getPersonId());
                setFirstName(person.getFirstName());
                setLastName(person.getLastName());
            }
        }
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities()
    {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(ROLE_API_APPLICATION_USER));
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return null;
    }

    @Override
    public String getUsername()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
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

    public String getAuthenticationToken()
    {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken)
    {
        this.authenticationToken = authenticationToken;
    }

    public Date getAuthenticationTokenExpiryDate()
    {
        return authenticationTokenExpiryDate;
    }

    public void setAuthenticationTokenExpiryDate(Date authenticationTokenExpiryDate)
    {
        this.authenticationTokenExpiryDate = authenticationTokenExpiryDate;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public LoginResponseDTO getLoginDetails()
    {
        return loginDetails;
    }

    public void setLoginDetails(LoginResponseDTO loginDetails)
    {
        this.loginDetails = loginDetails;
    }

    public String getPrimaryPersonId()
    {
        return primaryPersonId;
    }

    public void setPrimaryPersonId(String primaryPersonId)
    {
        this.primaryPersonId = primaryPersonId;
    }
}
