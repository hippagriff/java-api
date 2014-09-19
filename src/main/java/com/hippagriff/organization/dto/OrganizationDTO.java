package com.hippagriff.organization.dto;

/**
 * Represents Organization Data. Conforms to FHIR format
 * 
 * @author Smitha
 *
 */
public class OrganizationDTO
{
    private String reference;

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }

}
