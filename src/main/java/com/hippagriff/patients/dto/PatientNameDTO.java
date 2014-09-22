package com.hippagriff.patients.dto;

import java.util.List;

/**
 * Presents a Patient Name conforming to FHIR specification
 * @author Smitha
 *
 */
public class PatientNameDTO
{

    private String use;
    private List<String> family;
    private List<String> given;

    public String getUse()
    {
        return use;
    }

    public void setUse(String use)
    {
        this.use = use;
    }

    public List<String> getFamily()
    {
        return family;
    }

    public void setFamily(List<String> family)
    {
        this.family = family;
    }

    public List<String> getGiven()
    {
        return given;
    }

    public void setGiven(List<String> given)
    {
        this.given = given;
    }

}
