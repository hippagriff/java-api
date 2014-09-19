package com.hippagriff.patients.dto;

/**
 * This DTO represents a basic patient search request. It maintains all of the possible search criteria when searching
 * for a {@link PatientSystem}.
 * 
 * @author jon
 * 
 */
public class PatientSearchRequestDTO
{
    private String firstName;

    private String lastName;

    private String mrn;

    public PatientSearchRequestDTO()
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

    public String getMrn()
    {
        return mrn;
    }

    public void setMrn(String mrn)
    {
        this.mrn = mrn;
    }
}
