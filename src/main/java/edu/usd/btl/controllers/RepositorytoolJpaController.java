/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.controllers;

import edu.usd.btl.controllers.exceptions.NonexistentEntityException;
import edu.usd.btl.entities.Repositorytool;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Joseph
 */
public class RepositorytoolJpaController implements Serializable {

    public RepositorytoolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Repositorytool repositorytool) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(repositorytool);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Repositorytool repositorytool) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            repositorytool = em.merge(repositorytool);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = repositorytool.getId();
                if (findRepositorytool(id) == null) {
                    throw new NonexistentEntityException("The repositorytool with id " + id + " no longer exists.");
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
            Repositorytool repositorytool;
            try {
                repositorytool = em.getReference(Repositorytool.class, id);
                repositorytool.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The repositorytool with id " + id + " no longer exists.", enfe);
            }
            em.remove(repositorytool);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Repositorytool> findRepositorytoolEntities() {
        return findRepositorytoolEntities(true, -1, -1);
    }

    public List<Repositorytool> findRepositorytoolEntities(int maxResults, int firstResult) {
        return findRepositorytoolEntities(false, maxResults, firstResult);
    }

    private List<Repositorytool> findRepositorytoolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Repositorytool.class));
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

    public Repositorytool findRepositorytool(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Repositorytool.class, id);
        } finally {
            em.close();
        }
    }

    public int getRepositorytoolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Repositorytool> rt = cq.from(Repositorytool.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
