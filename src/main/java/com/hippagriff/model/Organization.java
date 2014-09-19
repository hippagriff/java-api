package com.hippagriff.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the Organization database table.
 * 
 */
@Entity
@Table(name = "Organization")
public class Organization extends com.hippagriff.model.BaseModel implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id")
    private String organizationId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "parent_organization_id")
    private String parentOrganizationId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;

    // bi-directional many-to-one association to Patient_System
    @OneToMany(mappedBy = "organization")
    private List<PatientSystem> patientSystems;

    public Organization()
    {
    }

    public String getOrganizationId()
    {
        return this.organizationId;
    }

    public void setOrganizationId(String organizationId)
    {
        this.organizationId = organizationId;
    }

    public Date getCreatedDate()
    {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getParentOrganizationId()
    {
        return this.parentOrganizationId;
    }

    public void setParentOrganizationId(String parentOrganizationId)
    {
        this.parentOrganizationId = parentOrganizationId;
    }

    public Date getUpdatedDate()
    {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

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