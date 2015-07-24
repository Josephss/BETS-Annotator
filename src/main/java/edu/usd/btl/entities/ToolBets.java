/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Joseph
 */
@Entity
@Table(name = "tool_bets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ToolBets.findAll", query = "SELECT t FROM ToolBets t"),
    @NamedQuery(name = "ToolBets.findById", query = "SELECT t FROM ToolBets t WHERE t.id = :id"),
    @NamedQuery(name = "ToolBets.findByName", query = "SELECT t FROM ToolBets t WHERE t.name = :name"),
    @NamedQuery(name = "ToolBets.findByVersion", query = "SELECT t FROM ToolBets t WHERE t.version = :version"),
    @NamedQuery(name = "ToolBets.findByDateAdded", query = "SELECT t FROM ToolBets t WHERE t.dateAdded = :dateAdded"),
    @NamedQuery(name = "ToolBets.findByDateUpdated", query = "SELECT t FROM ToolBets t WHERE t.dateUpdated = :dateUpdated"),
    @NamedQuery(name = "ToolBets.findByIcon", query = "SELECT t FROM ToolBets t WHERE t.icon = :icon")})
public class ToolBets implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "version")
    private String version;
    @Lob
    @Column(name = "summary")
    private String summary;
    @Lob
    @Column(name = "bets")
    private byte[] bets;
    @Column(name = "dateAdded")
    @Temporal(TemporalType.DATE)
    private Date dateAdded;
    @Basic(optional = false)
    @Column(name = "dateUpdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;
    @Column(name = "icon")
    private String icon;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toolId")
    // -------------------- //
    private Collection<ToolBridge> toolBridgeCollection;

    public ToolBets() {
    }

    public ToolBets(Integer id) {
        this.id = id;
    }

    public ToolBets(Integer id, String name, Date dateUpdated) {
        this.id = id;
        this.name = name;
        this.dateUpdated = dateUpdated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public byte[] getBets() {
        return bets;
    }

    public void setBets(byte[] bets) {
        this.bets = bets;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @XmlTransient
    public Collection<ToolBridge> getToolBridgeCollection() {
        return toolBridgeCollection;
    }

    public void setToolBridgeCollection(Collection<ToolBridge> toolBridgeCollection) {
        this.toolBridgeCollection = toolBridgeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ToolBets)) {
            return false;
        }
        ToolBets other = (ToolBets) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.usd.btl.annotate.ToolBets[ id=" + id + " ]";
    }
    
}
