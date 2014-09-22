package com.hippagriff.fhir.dto;

import java.util.Date;
import java.util.List;

import com.hippagriff.organization.dto.OrganizationDTO;
import com.hippagriff.patients.dto.PatientAddressDTO;
import com.hippagriff.patients.dto.PatientContactDTO;
import com.hippagriff.patients.dto.PatientGenderDTO;
import com.hippagriff.patients.dto.PatientIdentifierDTO;
import com.hippagriff.patients.dto.PatientNameDTO;
import com.hippagriff.patients.dto.PatientTelecomDTO;

/**
 * Patient Search results are captured in this Object. Conforms to FHIR Patient specification
 * 
 * @author Smitha
 */
public class PatientSearchResultFHIRDTO
{

    private String resourceType;
    private List<PatientIdentifierDTO> identifier;
    private List<PatientNameDTO> name;
    private List<PatientTelecomDTO> telecom;
    private List<PatientGenderDTO> gender;
    private Date birthDate;
    private boolean deceasedBoolean;
    private List<PatientAddressDTO> address;
    private List<PatientContactDTO> contact;
    private OrganizationDTO managingOrganization;

    public String getResourceType()
    {
        return resourceType;
    }

    public void setResourceType(String resourceType)
    {
        this.resourceType = resourceType;
    }

    public List<PatientIdentifierDTO> getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(List<PatientIdentifierDTO> identifier)
    {
        this.identifier = identifier;
    }

    public List<PatientNameDTO> getName()
    {
        return name;
    }

    public void setName(List<PatientNameDTO> name)
    {
        this.name = name;
    }

    public List<PatientTelecomDTO> getTelecom()
    {
        return telecom;
    }

    public void setTelecom(List<PatientTelecomDTO> telecom)
    {
        this.telecom = telecom;
    }

    public List<PatientGenderDTO> getGender()
    {
        return gender;
    }

    public void setGender(List<PatientGenderDTO> gender)
    {
        this.gender = gender;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public boolean isDeceasedBoolean()
    {
        return deceasedBoolean;
    }

    public void setDeceasedBoolean(boolean deceasedBoolean)
    {
        this.deceasedBoolean = deceasedBoolean;
    }

    public List<PatientAddressDTO> getAddress()
    {
        return address;
    }

    public void setAddress(List<PatientAddressDTO> address)
    {
        this.address = address;
    }

    public List<PatientContactDTO> getContact()
    {
        return contact;
    }

    public void setContact(List<PatientContactDTO> contact)
    {
        this.contact = contact;
    }

    public OrganizationDTO getManagingOrganization()
    {
        return managingOrganization;
    }

    public void setManagingOrganization(OrganizationDTO managingOrganization)
    {
        this.managingOrganization = managingOrganization;
    }

}
