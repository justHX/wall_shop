/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall_shop.entyties.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import wall_shop.entyties.Reservet;
import wall_shop.entyties.Selling;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author hulk-
 */
public class SellingJpaController implements Serializable {

    public SellingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Selling selling) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservet idReserv = selling.getIdReserv();
            if (idReserv != null) {
                idReserv = em.getReference(idReserv.getClass(), idReserv.getIdReserv());
                selling.setIdReserv(idReserv);
            }
            em.persist(selling);
            if (idReserv != null) {
                idReserv.getSellingCollection().add(selling);
                idReserv = em.merge(idReserv);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Selling selling) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Selling persistentSelling = em.find(Selling.class, selling.getIdSell());
            Reservet idReservOld = persistentSelling.getIdReserv();
            Reservet idReservNew = selling.getIdReserv();
            if (idReservNew != null) {
                idReservNew = em.getReference(idReservNew.getClass(), idReservNew.getIdReserv());
                selling.setIdReserv(idReservNew);
            }
            selling = em.merge(selling);
            if (idReservOld != null && !idReservOld.equals(idReservNew)) {
                idReservOld.getSellingCollection().remove(selling);
                idReservOld = em.merge(idReservOld);
            }
            if (idReservNew != null && !idReservNew.equals(idReservOld)) {
                idReservNew.getSellingCollection().add(selling);
                idReservNew = em.merge(idReservNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = selling.getIdSell();
                if (findSelling(id) == null) {
                    throw new NonexistentEntityException("The selling with id " + id + " no longer exists.");
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
            Selling selling;
            try {
                selling = em.getReference(Selling.class, id);
                selling.getIdSell();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The selling with id " + id + " no longer exists.", enfe);
            }
            Reservet idReserv = selling.getIdReserv();
            if (idReserv != null) {
                idReserv.getSellingCollection().remove(selling);
                idReserv = em.merge(idReserv);
            }
            em.remove(selling);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Selling> findSellingEntities() {
        return findSellingEntities(true, -1, -1);
    }

    public List<Selling> findSellingEntities(int maxResults, int firstResult) {
        return findSellingEntities(false, maxResults, firstResult);
    }

    private List<Selling> findSellingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Selling.class));
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

    public Selling findSelling(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Selling.class, id);
        } finally {
            em.close();
        }
    }

    public int getSellingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Selling> rt = cq.from(Selling.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
