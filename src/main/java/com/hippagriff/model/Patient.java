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
 * The persistent class for the Patient database table.
 * 
 */
@Entity
@Table(name = "Patient")
public class Patient extends com.hippagriff.model.BaseModel implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_mpi")
    private String patientMpi;

    // bi-directional many-to-one association to Patient_System
    @OneToMany(mappedBy = "patient")
    private List<PatientSystem> patientSystems;

    public Patient()
    {
    }

    public String getPatientMpi()
    {
        return this.patientMpi;
    }

    public void setPatientMpi(String patientMpi)
    {
        this.patientMpi = patientMpi;
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
        patientSystem.setPatient(this);

        return patientSystem;
    }

    public PatientSystem removePatientSystem(PatientSystem patientSystem)
    {
        getPatientSystems().remove(patientSystem);
        patientSystem.setPatient(null);

        return patientSystem;
    }

}