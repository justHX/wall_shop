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
import wall_shop.entyties.Delivery;
import wall_shop.entyties.Reservet;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author hulk-
 */
public class DeliveryJpaController implements Serializable {

    public DeliveryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Delivery delivery) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservet idReserv = delivery.getIdReserv();
            if (idReserv != null) {
                idReserv = em.getReference(idReserv.getClass(), idReserv.getIdReserv());
                delivery.setIdReserv(idReserv);
            }
            em.persist(delivery);
            if (idReserv != null) {
                idReserv.getDeliveryCollection().add(delivery);
                idReserv = em.merge(idReserv);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Delivery delivery) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Delivery persistentDelivery = em.find(Delivery.class, delivery.getIdDelivery());
            Reservet idReservOld = persistentDelivery.getIdReserv();
            Reservet idReservNew = delivery.getIdReserv();
            if (idReservNew != null) {
                idReservNew = em.getReference(idReservNew.getClass(), idReservNew.getIdReserv());
                delivery.setIdReserv(idReservNew);
            }
            delivery = em.merge(delivery);
            if (idReservOld != null && !idReservOld.equals(idReservNew)) {
                idReservOld.getDeliveryCollection().remove(delivery);
                idReservOld = em.merge(idReservOld);
            }
            if (idReservNew != null && !idReservNew.equals(idReservOld)) {
                idReservNew.getDeliveryCollection().add(delivery);
                idReservNew = em.merge(idReservNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = delivery.getIdDelivery();
                if (findDelivery(id) == null) {
                    throw new NonexistentEntityException("The delivery with id " + id + " no longer exists.");
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
            Delivery delivery;
            try {
                delivery = em.getReference(Delivery.class, id);
                delivery.getIdDelivery();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The delivery with id " + id + " no longer exists.", enfe);
            }
            Reservet idReserv = delivery.getIdReserv();
            if (idReserv != null) {
                idReserv.getDeliveryCollection().remove(delivery);
                idReserv = em.merge(idReserv);
            }
            em.remove(delivery);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Delivery> findDeliveryEntities() {
        return findDeliveryEntities(true, -1, -1);
    }

    public List<Delivery> findDeliveryEntities(int maxResults, int firstResult) {
        return findDeliveryEntities(false, maxResults, firstResult);
    }

    private List<Delivery> findDeliveryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Delivery.class));
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

    public Delivery findDelivery(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Delivery.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeliveryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Delivery> rt = cq.from(Delivery.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
