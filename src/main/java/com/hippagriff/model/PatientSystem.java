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

    private String patientSystemId;

    private String patientFirstName;

    private String patientLastName;

    private String patientMiddleName;

    private String patientMrn;

    private String patientPrefixName;

    private String patientSuffixName;

    private String systemId;

    private System system;

    private Organization organization;

    private Patient patient;

    public PatientSystem()
    {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_system_id")
    public String getPatientSystemId()
    {
        return this.patientSystemId;
    }

    public void setPatientSystemId(String patientSystemId)
    {
        this.patientSystemId = patientSystemId;
    }

    @Column(name = "patient_first_name")
    public String getPatientFirstName()
    {
        return this.patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName)
    {
        this.patientFirstName = patientFirstName;
    }

    @Column(name = "patient_last_name")
    public String getPatientLastName()
    {
        return this.patientLastName;
    }

    public void setPatientLastName(String patientLastName)
    {
        this.patientLastName = patientLastName;
    }

    @Column(name = "patient_middle_name")
    public String getPatientMiddleName()
    {
        return this.patientMiddleName;
    }

    public void setPatientMiddleName(String patientMiddleName)
    {
        this.patientMiddleName = patientMiddleName;
    }

    @Column(name = "patient_mrn")
    public String getPatientMrn()
    {
        return this.patientMrn;
    }

    public void setPatientMrn(String patientMrn)
    {
        this.patientMrn = patientMrn;
    }

    @Column(name = "patient_prefix_name")
    public String getPatientPrefixName()
    {
        return this.patientPrefixName;
    }

    public void setPatientPrefixName(String patientPrefixName)
    {
        this.patientPrefixName = patientPrefixName;
    }

    @Column(name = "patient_suffix_name")
    public String getPatientSuffixName()
    {
        return this.patientSuffixName;
    }

    public void setPatientSuffixName(String patientSuffixName)
    {
        this.patientSuffixName = patientSuffixName;
    }

    @Column(name = "system_id")
    public String getSystemId()
    {
        return this.systemId;
    }

    public void setSystemId(String systemId)
    {
        this.systemId = systemId;
    }

    // bi-directional one-to-one association to System
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_system_id")
    public System getSystem()
    {
        return this.system;
    }

    public void setSystem(System system)
    {
        this.system = system;
    }

    // bi-directional many-to-one association to Organization
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    public Organization getOrganization()
    {
        return this.organization;
    }

    public void setOrganization(Organization organization)
    {
        this.organization = organization;
    }

    // bi-directional many-to-one association to Patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_mpi")
    public Patient getPatient()
    {
        return this.patient;
    }

    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }

}