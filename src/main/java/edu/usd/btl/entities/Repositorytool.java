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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Joseph
 */
@Entity
@Table(name = "repositorytool")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Repositorytool.findAll", query = "SELECT r FROM Repositorytool r"),
    @NamedQuery(name = "Repositorytool.findByToolId", query = "SELECT r FROM Repositorytool r WHERE r.toolId = :toolId"),
    @NamedQuery(name = "Repositorytool.findByRepositoryId", query = "SELECT r FROM Repositorytool r WHERE r.repositoryId = :repositoryId"),
    @NamedQuery(name = "Repositorytool.findById", query = "SELECT r FROM Repositorytool r WHERE r.id = :id")})
public class Repositorytool implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "tool_id")
    private int toolId;
    @Basic(optional = false)
    @Column(name = "repository_id")
    private int repositoryId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public Repositorytool() {
    }

    public Repositorytool(Integer id) {
        this.id = id;
    }

    public Repositorytool(Integer id, int toolId, int repositoryId) {
        this.id = id;
        this.toolId = toolId;
        this.repositoryId = repositoryId;
    }

    public int getToolId() {
        return toolId;
    }

    public void setToolId(int toolId) {
        this.toolId = toolId;
    }

    public int getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Repositorytool)) {
            return false;
        }
        Repositorytool other = (Repositorytool) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.usd.btl.annotate.Repositorytool[ id=" + id + " ]";
    }
    
}
