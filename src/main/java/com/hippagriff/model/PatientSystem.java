package com.hippagriff.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the PatientSystem database table.
 * 
 */
@Entity
@Table(name = "Patient_System")
public class PatientSystem extends com.hippagriff.model.BaseModel implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_system_id")
    private String patientSystemId;

    @Column(name = "patient_first_name")
    private String patientFirstName;

    @Column(name = "patient_last_name")
    private String patientLastName;

    @Column(name = "patient_middle_name")
    private String patientMiddleName;

    @Column(name = "patient_mrn")
    private String patientMrn;

    @Column(name = "patient_prefix_name")
    private String patientPrefixName;

    @Column(name = "patient_suffix_name")
    private String patientSuffixName;

    @Column(name = "system_id")
    private String systemId;

    // bi-directional one-to-one association to System
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_system_id")
    private System system;

    // bi-directional many-to-one association to Organization
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    // bi-directional many-to-one association to Patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_mpi")
    private Patient patient;

    public PatientSystem()
    {
    }

    public String getPatientSystemId()
    {
        return this.patientSystemId;
    }

    public void setPatientSystemId(String patientSystemId)
    {
        this.patientSystemId = patientSystemId;
    }

    public String getPatientFirstName()
    {
        return this.patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName)
    {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName()
    {
        return this.patientLastName;
    }

    public void setPatientLastName(String patientLastName)
    {
        this.patientLastName = patientLastName;
    }

    public String getPatientMiddleName()
    {
        return this.patientMiddleName;
    }

    public void setPatientMiddleName(String patientMiddleName)
    {
        this.patientMiddleName = patientMiddleName;
    }

    public String getPatientMrn()
    {
        return this.patientMrn;
    }

    public void setPatientMrn(String patientMrn)
    {
        this.patientMrn = patientMrn;
    }

    public String getPatientPrefixName()
    {
        return this.patientPrefixName;
    }

    public void setPatientPrefixName(String patientPrefixName)
    {
        this.patientPrefixName = patientPrefixName;
    }

    public String getPatientSuffixName()
    {
        return this.patientSuffixName;
    }

    public void setPatientSuffixName(String patientSuffixName)
    {
        this.patientSuffixName = patientSuffixName;
    }

    public String getSystemId()
    {
        return this.systemId;
    }

    public void setSystemId(String systemId)
    {
        this.systemId = systemId;
    }

    public System getSystem()
    {
        return this.system;
    }

    public void setSystem(System system)
    {
        this.system = system;
    }

    public Organization getOrganization()
    {
        return this.organization;
    }

    public void setOrganization(Organization organization)
    {
        this.organization = organization;
    }

    public Patient getPatient()
    {
        return this.patient;
    }

    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }

}