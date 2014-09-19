package com.hippagriff.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the System database table.
 * 
 */
@Entity
@Table(name = "System")
public class System extends com.hippagriff.model.BaseModel implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_id")
    private String systemId;

    // bi-directional one-to-one association to Patient_System
    @OneToOne(mappedBy = "system", fetch = FetchType.LAZY)
    private PatientSystem patientSystem;

    public System()
    {
    }

    public String getSystemId()
    {
        return this.systemId;
    }

    public void setSystemId(String systemId)
    {
        this.systemId = systemId;
    }

    public PatientSystem getPatientSystem()
    {
        return this.patientSystem;
    }

    public void setPatientSystem(PatientSystem patientSystem)
    {
        this.patientSystem = patientSystem;
    }

}