package com.lumira.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Base class to derive entity classes from
 * @author jon
 *
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable
{
    private static final long serialVersionUID = -8832941742099564261L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CRTD_DTTM")
    private Date createdDate;

    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate()
    {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    @PrePersist
    @PreUpdate
    void createAndUpdateAtCallback()
    {
        if (getId() == null)
        {
            setCreatedDate(new Date());
        }
    }

}
