package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Organization database table.
 * 
 */
@Entity
@NamedQuery(name="Organization.findAll", query="SELECT o FROM Organization o")
public class Organization extends com.hippagriff.model.BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="organization_id")
	private String organizationId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="parent_organization_id")
	private String parentOrganizationId;

	private byte status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

	//bi-directional many-to-one association to Patient_System
	@OneToMany(mappedBy="organization")
	private List<Patient_System> patientSystems;

	public Organization() {
	}

	public String getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getParentOrganizationId() {
		return this.parentOrganizationId;
	}

	public void setParentOrganizationId(String parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<Patient_System> getPatientSystems() {
		return this.patientSystems;
	}

	public void setPatientSystems(List<Patient_System> patientSystems) {
		this.patientSystems = patientSystems;
	}

	public Patient_System addPatientSystem(Patient_System patientSystem) {
		getPatientSystems().add(patientSystem);
		patientSystem.setOrganization(this);

		return patientSystem;
	}

	public Patient_System removePatientSystem(Patient_System patientSystem) {
		getPatientSystems().remove(patientSystem);
		patientSystem.setOrganization(null);

		return patientSystem;
	}

}