package com.hippagriff.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.hippagriff.ldap.config.LDAPCommonService;

/**
 * Base entity for all models that contains CRUD operations
 * 
 * @author jon,smitha
 */
@MappedSuperclass
public abstract class BaseModel
{
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createDate;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updateDate;

    @Column(name = "CREATE_SOURCE", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "UPDATE_SOURCE", nullable = false, length = 50)
    private String updatedBy;

    @Column(name = "STATUS", nullable = true, length = 1)
    private boolean status;

    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy()
    {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public boolean isNew()
    {
        return (getCreateDate() == null);
    }

    @PrePersist
    @PreUpdate
    public void updateCRUD()
    {
        String createdBy = LDAPCommonService.getCurrentHost();
        Date now = LDAPCommonService.getNow();

        if (getCreateDate() == null)
        {
            setCreateDate(now);
        }

        if (getCreatedBy() == null)
        {
            setCreatedBy(createdBy);
        }

        setUpdateDate(now);
        setUpdatedBy(createdBy);

    }

}
