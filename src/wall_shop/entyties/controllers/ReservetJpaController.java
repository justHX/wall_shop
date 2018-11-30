/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall_shop.entyties.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import wall_shop.entyties.InfoGarages;
import wall_shop.entyties.Clients;
import wall_shop.entyties.Delivery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import wall_shop.entyties.Reservet;
import wall_shop.entyties.Selling;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author hulk-
 */
public class ReservetJpaController implements Serializable {

    public ReservetJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reservet reservet) {
        if (reservet.getDeliveryCollection() == null) {
            reservet.setDeliveryCollection(new ArrayList<Delivery>());
        }
        if (reservet.getSellingCollection() == null) {
            reservet.setSellingCollection(new ArrayList<Selling>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InfoGarages idinfGar = reservet.getIdinfGar();
            if (idinfGar != null) {
                idinfGar = em.getReference(idinfGar.getClass(), idinfGar.getIdinfGar());
                reservet.setIdinfGar(idinfGar);
            }
            Clients idClient = reservet.getIdClient();
            if (idClient != null) {
                idClient = em.getReference(idClient.getClass(), idClient.getIdClient());
                reservet.setIdClient(idClient);
            }
            Collection<Delivery> attachedDeliveryCollection = new ArrayList<Delivery>();
            for (Delivery deliveryCollectionDeliveryToAttach : reservet.getDeliveryCollection()) {
                deliveryCollectionDeliveryToAttach = em.getReference(deliveryCollectionDeliveryToAttach.getClass(), deliveryCollectionDeliveryToAttach.getIdDelivery());
                attachedDeliveryCollection.add(deliveryCollectionDeliveryToAttach);
            }
            reservet.setDeliveryCollection(attachedDeliveryCollection);
            Collection<Selling> attachedSellingCollection = new ArrayList<Selling>();
            for (Selling sellingCollectionSellingToAttach : reservet.getSellingCollection()) {
                sellingCollectionSellingToAttach = em.getReference(sellingCollectionSellingToAttach.getClass(), sellingCollectionSellingToAttach.getIdSell());
                attachedSellingCollection.add(sellingCollectionSellingToAttach);
            }
            reservet.setSellingCollection(attachedSellingCollection);
            em.persist(reservet);
            if (idinfGar != null) {
                idinfGar.getReservetCollection().add(reservet);
                idinfGar = em.merge(idinfGar);
            }
            if (idClient != null) {
                idClient.getReservetCollection().add(reservet);
                idClient = em.merge(idClient);
            }
            for (Delivery deliveryCollectionDelivery : reservet.getDeliveryCollection()) {
                Reservet oldIdReservOfDeliveryCollectionDelivery = deliveryCollectionDelivery.getIdReserv();
                deliveryCollectionDelivery.setIdReserv(reservet);
                deliveryCollectionDelivery = em.merge(deliveryCollectionDelivery);
                if (oldIdReservOfDeliveryCollectionDelivery != null) {
                    oldIdReservOfDeliveryCollectionDelivery.getDeliveryCollection().remove(deliveryCollectionDelivery);
                    oldIdReservOfDeliveryCollectionDelivery = em.merge(oldIdReservOfDeliveryCollectionDelivery);
                }
            }
            for (Selling sellingCollectionSelling : reservet.getSellingCollection()) {
                Reservet oldIdReservOfSellingCollectionSelling = sellingCollectionSelling.getIdReserv();
                sellingCollectionSelling.setIdReserv(reservet);
                sellingCollectionSelling = em.merge(sellingCollectionSelling);
                if (oldIdReservOfSellingCollectionSelling != null) {
                    oldIdReservOfSellingCollectionSelling.getSellingCollection().remove(sellingCollectionSelling);
                    oldIdReservOfSellingCollectionSelling = em.merge(oldIdReservOfSellingCollectionSelling);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reservet reservet) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservet persistentReservet = em.find(Reservet.class, reservet.getIdReserv());
            InfoGarages idinfGarOld = persistentReservet.getIdinfGar();
            InfoGarages idinfGarNew = reservet.getIdinfGar();
            Clients idClientOld = persistentReservet.getIdClient();
            Clients idClientNew = reservet.getIdClient();
            Collection<Delivery> deliveryCollectionOld = persistentReservet.getDeliveryCollection();
            Collection<Delivery> deliveryCollectionNew = reservet.getDeliveryCollection();
            Collection<Selling> sellingCollectionOld = persistentReservet.getSellingCollection();
            Collection<Selling> sellingCollectionNew = reservet.getSellingCollection();
            if (idinfGarNew != null) {
                idinfGarNew = em.getReference(idinfGarNew.getClass(), idinfGarNew.getIdinfGar());
                reservet.setIdinfGar(idinfGarNew);
            }
            if (idClientNew != null) {
                idClientNew = em.getReference(idClientNew.getClass(), idClientNew.getIdClient());
                reservet.setIdClient(idClientNew);
            }
            Collection<Delivery> attachedDeliveryCollectionNew = new ArrayList<Delivery>();
            for (Delivery deliveryCollectionNewDeliveryToAttach : deliveryCollectionNew) {
                deliveryCollectionNewDeliveryToAttach = em.getReference(deliveryCollectionNewDeliveryToAttach.getClass(), deliveryCollectionNewDeliveryToAttach.getIdDelivery());
                attachedDeliveryCollectionNew.add(deliveryCollectionNewDeliveryToAttach);
            }
            deliveryCollectionNew = attachedDeliveryCollectionNew;
            reservet.setDeliveryCollection(deliveryCollectionNew);
            Collection<Selling> attachedSellingCollectionNew = new ArrayList<Selling>();
            for (Selling sellingCollectionNewSellingToAttach : sellingCollectionNew) {
                sellingCollectionNewSellingToAttach = em.getReference(sellingCollectionNewSellingToAttach.getClass(), sellingCollectionNewSellingToAttach.getIdSell());
                attachedSellingCollectionNew.add(sellingCollectionNewSellingToAttach);
            }
            sellingCollectionNew = attachedSellingCollectionNew;
            reservet.setSellingCollection(sellingCollectionNew);
            reservet = em.merge(reservet);
            if (idinfGarOld != null && !idinfGarOld.equals(idinfGarNew)) {
                idinfGarOld.getReservetCollection().remove(reservet);
                idinfGarOld = em.merge(idinfGarOld);
            }
            if (idinfGarNew != null && !idinfGarNew.equals(idinfGarOld)) {
                idinfGarNew.getReservetCollection().add(reservet);
                idinfGarNew = em.merge(idinfGarNew);
            }
            if (idClientOld != null && !idClientOld.equals(idClientNew)) {
                idClientOld.getReservetCollection().remove(reservet);
                idClientOld = em.merge(idClientOld);
            }
            if (idClientNew != null && !idClientNew.equals(idClientOld)) {
                idClientNew.getReservetCollection().add(reservet);
                idClientNew = em.merge(idClientNew);
            }
            for (Delivery deliveryCollectionOldDelivery : deliveryCollectionOld) {
                if (!deliveryCollectionNew.contains(deliveryCollectionOldDelivery)) {
                    deliveryCollectionOldDelivery.setIdReserv(null);
                    deliveryCollectionOldDelivery = em.merge(deliveryCollectionOldDelivery);
                }
            }
            for (Delivery deliveryCollectionNewDelivery : deliveryCollectionNew) {
                if (!deliveryCollectionOld.contains(deliveryCollectionNewDelivery)) {
                    Reservet oldIdReservOfDeliveryCollectionNewDelivery = deliveryCollectionNewDelivery.getIdReserv();
                    deliveryCollectionNewDelivery.setIdReserv(reservet);
                    deliveryCollectionNewDelivery = em.merge(deliveryCollectionNewDelivery);
                    if (oldIdReservOfDeliveryCollectionNewDelivery != null && !oldIdReservOfDeliveryCollectionNewDelivery.equals(reservet)) {
                        oldIdReservOfDeliveryCollectionNewDelivery.getDeliveryCollection().remove(deliveryCollectionNewDelivery);
                        oldIdReservOfDeliveryCollectionNewDelivery = em.merge(oldIdReservOfDeliveryCollectionNewDelivery);
                    }
                }
            }
            for (Selling sellingCollectionOldSelling : sellingCollectionOld) {
                if (!sellingCollectionNew.contains(sellingCollectionOldSelling)) {
                    sellingCollectionOldSelling.setIdReserv(null);
                    sellingCollectionOldSelling = em.merge(sellingCollectionOldSelling);
                }
            }
            for (Selling sellingCollectionNewSelling : sellingCollectionNew) {
                if (!sellingCollectionOld.contains(sellingCollectionNewSelling)) {
                    Reservet oldIdReservOfSellingCollectionNewSelling = sellingCollectionNewSelling.getIdReserv();
                    sellingCollectionNewSelling.setIdReserv(reservet);
                    sellingCollectionNewSelling = em.merge(sellingCollectionNewSelling);
                    if (oldIdReservOfSellingCollectionNewSelling != null && !oldIdReservOfSellingCollectionNewSelling.equals(reservet)) {
                        oldIdReservOfSellingCollectionNewSelling.getSellingCollection().remove(sellingCollectionNewSelling);
                        oldIdReservOfSellingCollectionNewSelling = em.merge(oldIdReservOfSellingCollectionNewSelling);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservet.getIdReserv();
                if (findReservet(id) == null) {
                    throw new NonexistentEntityException("The reservet with id " + id + " no longer exists.");
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
            Reservet reservet;
            try {
                reservet = em.getReference(Reservet.class, id);
                reservet.getIdReserv();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservet with id " + id + " no longer exists.", enfe);
            }
            InfoGarages idinfGar = reservet.getIdinfGar();
            if (idinfGar != null) {
                idinfGar.getReservetCollection().remove(reservet);
                idinfGar = em.merge(idinfGar);
            }
            Clients idClient = reservet.getIdClient();
            if (idClient != null) {
                idClient.getReservetCollection().remove(reservet);
                idClient = em.merge(idClient);
            }
            Collection<Delivery> deliveryCollection = reservet.getDeliveryCollection();
            for (Delivery deliveryCollectionDelivery : deliveryCollection) {
                deliveryCollectionDelivery.setIdReserv(null);
                deliveryCollectionDelivery = em.merge(deliveryCollectionDelivery);
            }
            Collection<Selling> sellingCollection = reservet.getSellingCollection();
            for (Selling sellingCollectionSelling : sellingCollection) {
                sellingCollectionSelling.setIdReserv(null);
                sellingCollectionSelling = em.merge(sellingCollectionSelling);
            }
            em.remove(reservet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reservet> findReservetEntities() {
        return findReservetEntities(true, -1, -1);
    }

    public List<Reservet> findReservetEntities(int maxResults, int firstResult) {
        return findReservetEntities(false, maxResults, firstResult);
    }

    private List<Reservet> findReservetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reservet.class));
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

    public Reservet findReservet(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reservet.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reservet> rt = cq.from(Reservet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
