package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Patient database table.
 * 
 */
@Entity
@NamedQuery(name="Patient.findAll", query="SELECT p FROM Patient p")
public class Patient extends com.hippagriff.model.BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="patient_mpi")
	private String patientMpi;

	
	//bi-directional many-to-one association to Patient_System
	@OneToMany(mappedBy="patient")
	private List<Patient_System> patientSystems;

	public Patient() {
	}

	public String getPatientMpi() {
		return this.patientMpi;
	}

	public void setPatientMpi(String patientMpi) {
		this.patientMpi = patientMpi;
	}

	public List<Patient_System> getPatientSystems() {
		return this.patientSystems;
	}

	public void setPatientSystems(List<Patient_System> patientSystems) {
		this.patientSystems = patientSystems;
	}

	public Patient_System addPatientSystem(Patient_System patientSystem) {
		getPatientSystems().add(patientSystem);
		patientSystem.setPatient(this);

		return patientSystem;
	}

	public Patient_System removePatientSystem(Patient_System patientSystem) {
		getPatientSystems().remove(patientSystem);
		patientSystem.setPatient(null);

		return patientSystem;
	}

}