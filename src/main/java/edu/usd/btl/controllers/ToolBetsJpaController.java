/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.controllers;

import edu.usd.btl.controllers.exceptions.IllegalOrphanException;
import edu.usd.btl.controllers.exceptions.NonexistentEntityException;
import edu.usd.btl.entities.ToolBets;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.usd.btl.entities.ToolBridge;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Joseph
 */
public class ToolBetsJpaController implements Serializable {

    public ToolBetsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ToolBets toolBets) {
        if (toolBets.getToolBridgeCollection() == null) {
            toolBets.setToolBridgeCollection(new ArrayList<ToolBridge>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ToolBridge> attachedToolBridgeCollection = new ArrayList<ToolBridge>();
            for (ToolBridge toolBridgeCollectionToolBridgeToAttach : toolBets.getToolBridgeCollection()) {
                toolBridgeCollectionToolBridgeToAttach = em.getReference(toolBridgeCollectionToolBridgeToAttach.getClass(), toolBridgeCollectionToolBridgeToAttach.getId());
                attachedToolBridgeCollection.add(toolBridgeCollectionToolBridgeToAttach);
            }
            toolBets.setToolBridgeCollection(attachedToolBridgeCollection);
            em.persist(toolBets);
            for (ToolBridge toolBridgeCollectionToolBridge : toolBets.getToolBridgeCollection()) {
                ToolBets oldToolIdOfToolBridgeCollectionToolBridge = toolBridgeCollectionToolBridge.getToolId();
                toolBridgeCollectionToolBridge.setToolId(toolBets);
                toolBridgeCollectionToolBridge = em.merge(toolBridgeCollectionToolBridge);
                if (oldToolIdOfToolBridgeCollectionToolBridge != null) {
                    oldToolIdOfToolBridgeCollectionToolBridge.getToolBridgeCollection().remove(toolBridgeCollectionToolBridge);
                    oldToolIdOfToolBridgeCollectionToolBridge = em.merge(oldToolIdOfToolBridgeCollectionToolBridge);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ToolBets toolBets) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ToolBets persistentToolBets = em.find(ToolBets.class, toolBets.getId());
            Collection<ToolBridge> toolBridgeCollectionOld = persistentToolBets.getToolBridgeCollection();
            Collection<ToolBridge> toolBridgeCollectionNew = toolBets.getToolBridgeCollection();
            List<String> illegalOrphanMessages = null;
            for (ToolBridge toolBridgeCollectionOldToolBridge : toolBridgeCollectionOld) {
                if (!toolBridgeCollectionNew.contains(toolBridgeCollectionOldToolBridge)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ToolBridge " + toolBridgeCollectionOldToolBridge + " since its toolId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ToolBridge> attachedToolBridgeCollectionNew = new ArrayList<ToolBridge>();
            for (ToolBridge toolBridgeCollectionNewToolBridgeToAttach : toolBridgeCollectionNew) {
                toolBridgeCollectionNewToolBridgeToAttach = em.getReference(toolBridgeCollectionNewToolBridgeToAttach.getClass(), toolBridgeCollectionNewToolBridgeToAttach.getId());
                attachedToolBridgeCollectionNew.add(toolBridgeCollectionNewToolBridgeToAttach);
            }
            toolBridgeCollectionNew = attachedToolBridgeCollectionNew;
            toolBets.setToolBridgeCollection(toolBridgeCollectionNew);
            toolBets = em.merge(toolBets);
            for (ToolBridge toolBridgeCollectionNewToolBridge : toolBridgeCollectionNew) {
                if (!toolBridgeCollectionOld.contains(toolBridgeCollectionNewToolBridge)) {
                    ToolBets oldToolIdOfToolBridgeCollectionNewToolBridge = toolBridgeCollectionNewToolBridge.getToolId();
                    toolBridgeCollectionNewToolBridge.setToolId(toolBets);
                    toolBridgeCollectionNewToolBridge = em.merge(toolBridgeCollectionNewToolBridge);
                    if (oldToolIdOfToolBridgeCollectionNewToolBridge != null && !oldToolIdOfToolBridgeCollectionNewToolBridge.equals(toolBets)) {
                        oldToolIdOfToolBridgeCollectionNewToolBridge.getToolBridgeCollection().remove(toolBridgeCollectionNewToolBridge);
                        oldToolIdOfToolBridgeCollectionNewToolBridge = em.merge(oldToolIdOfToolBridgeCollectionNewToolBridge);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = toolBets.getId();
                if (findToolBets(id) == null) {
                    throw new NonexistentEntityException("The toolBets with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ToolBets toolBets;
            try {
                toolBets = em.getReference(ToolBets.class, id);
                toolBets.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The toolBets with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ToolBridge> toolBridgeCollectionOrphanCheck = toolBets.getToolBridgeCollection();
            for (ToolBridge toolBridgeCollectionOrphanCheckToolBridge : toolBridgeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ToolBets (" + toolBets + ") cannot be destroyed since the ToolBridge " + toolBridgeCollectionOrphanCheckToolBridge + " in its toolBridgeCollection field has a non-nullable toolId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(toolBets);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ToolBets> findToolBetsEntities() {
        return findToolBetsEntities(true, -1, -1);
    }

    public List<ToolBets> findToolBetsEntities(int maxResults, int firstResult) {
        return findToolBetsEntities(false, maxResults, firstResult);
    }

    private List<ToolBets> findToolBetsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ToolBets.class));
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

    public ToolBets findToolBets(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ToolBets.class, id);
        } finally {
            em.close();
        }
    }

    public int getToolBetsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ToolBets> rt = cq.from(ToolBets.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
