/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.controllers;

import edu.usd.btl.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.usd.btl.entities.ToolBets;
import edu.usd.btl.entities.ToolBridge;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Joseph
 */
public class ToolBridgeJpaController implements Serializable {

    public ToolBridgeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ToolBridge toolBridge) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ToolBets toolId = toolBridge.getToolId();
            if (toolId != null) {
                toolId = em.getReference(toolId.getClass(), toolId.getId());
                toolBridge.setToolId(toolId);
            }
            em.persist(toolBridge);
            if (toolId != null) {
                toolId.getToolBridgeCollection().add(toolBridge);
                toolId = em.merge(toolId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ToolBridge toolBridge) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ToolBridge persistentToolBridge = em.find(ToolBridge.class, toolBridge.getId());
            ToolBets toolIdOld = persistentToolBridge.getToolId();
            ToolBets toolIdNew = toolBridge.getToolId();
            if (toolIdNew != null) {
                toolIdNew = em.getReference(toolIdNew.getClass(), toolIdNew.getId());
                toolBridge.setToolId(toolIdNew);
            }
            toolBridge = em.merge(toolBridge);
            if (toolIdOld != null && !toolIdOld.equals(toolIdNew)) {
                toolIdOld.getToolBridgeCollection().remove(toolBridge);
                toolIdOld = em.merge(toolIdOld);
            }
            if (toolIdNew != null && !toolIdNew.equals(toolIdOld)) {
                toolIdNew.getToolBridgeCollection().add(toolBridge);
                toolIdNew = em.merge(toolIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = toolBridge.getId();
                if (findToolBridge(id) == null) {
                    throw new NonexistentEntityException("The toolBridge with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ToolBridge toolBridge;
            try {
                toolBridge = em.getReference(ToolBridge.class, id);
                toolBridge.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The toolBridge with id " + id + " no longer exists.", enfe);
            }
            ToolBets toolId = toolBridge.getToolId();
            if (toolId != null) {
                toolId.getToolBridgeCollection().remove(toolBridge);
                toolId = em.merge(toolId);
            }
            em.remove(toolBridge);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ToolBridge> findToolBridgeEntities() {
        return findToolBridgeEntities(true, -1, -1);
    }

    public List<ToolBridge> findToolBridgeEntities(int maxResults, int firstResult) {
        return findToolBridgeEntities(false, maxResults, firstResult);
    }

    private List<ToolBridge> findToolBridgeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ToolBridge.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ToolBridge findToolBridge(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ToolBridge.class, id);
        } finally {
            em.close();
        }
    }

    public int getToolBridgeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ToolBridge> rt = cq.from(ToolBridge.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
