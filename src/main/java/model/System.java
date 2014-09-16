package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the System database table.
 * 
 */
@Entity
@NamedQuery(name="System.findAll", query="SELECT s FROM System s")
public class System extends com.hippagriff.model.BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="system_id")
	private String systemId;

	private byte status;

	//bi-directional one-to-one association to Patient_System
	@OneToOne(mappedBy="system", fetch=FetchType.LAZY)
	private Patient_System patientSystem;

	public System() {
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Patient_System getPatientSystem() {
		return this.patientSystem;
	}

	public void setPatientSystem(Patient_System patientSystem) {
		this.patientSystem = patientSystem;
	}

}