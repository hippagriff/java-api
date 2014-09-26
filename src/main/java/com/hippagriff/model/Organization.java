package com.hippagriff.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the Organization database table.
 * 
 */
@Entity
@Table(name = "Organization")
public class Organization extends com.hippagriff.model.BaseModel implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String organizationId;

    private String parentOrganizationId;

    private List<PatientSystem> patientSystems;

    public Organization()
    {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id")
    public String getOrganizationId()
    {
        return this.organizationId;
    }

    public void setOrganizationId(String organizationId)
    {
        this.organizationId = organizationId;
    }

    @Column(name = "parent_organization_id")
    public String getParentOrganizationId()
    {
        return this.parentOrganizationId;
    }

    public void setParentOrganizationId(String parentOrganizationId)
    {
        this.parentOrganizationId = parentOrganizationId;
    }

    // bi-directional many-to-one association to Patient_System
    @OneToMany(mappedBy = "organization")
    public List<PatientSystem> getPatientSystems()
    {
        return this.patientSystems;
    }

    public void setPatientSystems(List<PatientSystem> patientSystems)
    {
        this.patientSystems = patientSystems;
    }

    public PatientSystem addPatientSystem(PatientSystem patientSystem)
    {
        getPatientSystems().add(patientSystem);
        patientSystem.setOrganization(this);

        return patientSystem;
    }

    public PatientSystem removePatientSystem(PatientSystem patientSystem)
    {
        getPatientSystems().remove(patientSystem);
        patientSystem.setOrganization(null);

        return patientSystem;
    }

}