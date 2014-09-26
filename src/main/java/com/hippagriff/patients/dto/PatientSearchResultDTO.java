package com.hippagriff.patients.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for a a search result for a user that exists in the LDAP server.
 * 
 * @author jon
 * 
 */
public class PatientSearchResultDTO  implements Serializable
{
    private static final long serialVersionUID = 1286544550561223606L;

    private String id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String organizationId;

    private Date dateOfBirth;

    private String gender;

    private String suffix;

    private String prefix;

    private String mpi;

    private String mrn;

    public PatientSearchResultDTO()
    {
        // do nothing here
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getOrganizationId()
    {
        return organizationId;
    }

    public void setOrganizationId(String organizationId)
    {
        this.organizationId = organizationId;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public String getMpi()
    {
        return mpi;
    }

    public void setMpi(String mpi)
    {
        this.mpi = mpi;
    }

    public String getMrn()
    {
        return mrn;
    }

    public void setMrn(String mrn)
    {
        this.mrn = mrn;
    }
}
