/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Joseph
 */
@Entity
@Table(name = "tool_bridge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ToolBridge.findAll", query = "SELECT t FROM ToolBridge t"),
    @NamedQuery(name = "ToolBridge.findById", query = "SELECT t FROM ToolBridge t WHERE t.id = :id"),
    @NamedQuery(name = "ToolBridge.findByOntologyId", query = "SELECT t FROM ToolBridge t WHERE t.ontologyId = :ontologyId")})
public class ToolBridge implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ontology_id")
    private String ontologyId;
    @JoinColumn(name = "tool_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ToolBets toolId;

    public ToolBridge() {
    }

    public ToolBridge(Integer id) {
        this.id = id;
    }

    public ToolBridge(Integer id, String ontologyId) {
        this.id = id;
        this.ontologyId = ontologyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOntologyId() {
        return ontologyId;
    }

    public void setOntologyId(String ontologyId) {
        this.ontologyId = ontologyId;
    }

    public ToolBets getToolId() {
        return toolId;
    }

    public void setToolId(ToolBets toolId) {
        this.toolId = toolId;
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
        if (!(object instanceof ToolBridge)) {
            return false;
        }
        ToolBridge other = (ToolBridge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.usd.btl.annotate.ToolBridge[ id=" + id + " ]";
    }
    
}
