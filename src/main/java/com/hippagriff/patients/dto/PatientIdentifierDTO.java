package com.hippagriff.patients.dto;


/**
 * Represents data to identify a Patient. Conforms to FHIR specification
 * 
 * @author Smitha
 *
 */
public class PatientIdentifierDTO
{

    private String label;
    private String system;
    private String value;
    private String assigner;

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getSystem()
    {
        return system;
    }

    public void setSystem(String system)
    {
        this.system = system;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getAssigner()
    {
        return assigner;
    }

    public void setAssigner(String assigner)
    {
        this.assigner = assigner;
    }

}
