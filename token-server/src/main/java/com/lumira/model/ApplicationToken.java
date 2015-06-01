package com.lumira.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This record encapsulates a relationship between an application token and the server it points to.
 * 
 * @author jon
 * 
 */
@Entity
@Table(name = "CONNECT_DIRECTORY")
@JsonAutoDetect(getterVisibility = Visibility.NONE, fieldVisibility = Visibility.ANY)
@JsonInclude(Include.NON_NULL)
@ApiObject(name = "ApplicationToken")
public class ApplicationToken extends AbstractEntity
{
    private static final long serialVersionUID = -4461058609266878420L;

    @ApiObjectField(description = "The specific token used to access the host.", required = true)
    @JsonProperty("connect_key")
    @Column(name = "CONNECT_KEY")
    private String connectKey;

    @ApiObjectField(description = "The host URL that should be used to access the Lumira API.")
    @JsonProperty("host")
    @Column(name = "HOST")
    private String host;

    @ApiObjectField(description = "Identifier of the device to last activate the token.")
    @JsonProperty("device_identity")
    @Column(name = "DEVICE_IDENTITY")
    private String deviceIdentity;

    @ApiObjectField(description = "Date where token was most recently activated.")
    @JsonProperty("activated_date")
    @Column(name = "ACTIVATED_DTTM")
    private Date activatedDate;

    @Override
    public String toString()
    {
        return "[ConnectFactory id : " + this.getId() + ", Connect Key : " + this.getConnectKey() + ", Host : "
                + this.getHost() + ", Device Identity : " + this.getDeviceIdentity() + "]";
    }

    /**
     * @return the connectKey
     */
    public String getConnectKey()
    {
        return connectKey;
    }

    /**
     * @param connectKey the connectKey to set
     */
    public void setConnectKey(String connectKey)
    {
        this.connectKey = connectKey;
    }

    /**
     * @return the host
     */
    public String getHost()
    {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host)
    {
        this.host = host;
    }

    /**
     * @return the deviceIdentity
     */
    public String getDeviceIdentity()
    {
        return deviceIdentity;
    }

    /**
     * @param deviceIdentity the deviceIdentity to set
     */
    public void setDeviceIdentity(String deviceIdentity)
    {
        this.deviceIdentity = deviceIdentity;
    }

    /**
     * @return the activatedDate
     */
    public Date getActivatedDate()
    {
        return activatedDate;
    }

    /**
     * @param activatedDate the activatedDate to set
     */
    public void setActivatedDate(Date activatedDate)
    {
        this.activatedDate = activatedDate;
    }
}
