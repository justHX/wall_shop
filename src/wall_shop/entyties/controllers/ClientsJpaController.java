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
import wall_shop.entyties.Reservet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import wall_shop.entyties.Clients;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author hulk-
 */
public class ClientsJpaController implements Serializable {

    public ClientsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clients clients) {
        if (clients.getReservetCollection() == null) {
            clients.setReservetCollection(new ArrayList<Reservet>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Reservet> attachedReservetCollection = new ArrayList<Reservet>();
            for (Reservet reservetCollectionReservetToAttach : clients.getReservetCollection()) {
                reservetCollectionReservetToAttach = em.getReference(reservetCollectionReservetToAttach.getClass(), reservetCollectionReservetToAttach.getIdReserv());
                attachedReservetCollection.add(reservetCollectionReservetToAttach);
            }
            clients.setReservetCollection(attachedReservetCollection);
            em.persist(clients);
            for (Reservet reservetCollectionReservet : clients.getReservetCollection()) {
                Clients oldIdClientOfReservetCollectionReservet = reservetCollectionReservet.getIdClient();
                reservetCollectionReservet.setIdClient(clients);
                reservetCollectionReservet = em.merge(reservetCollectionReservet);
                if (oldIdClientOfReservetCollectionReservet != null) {
                    oldIdClientOfReservetCollectionReservet.getReservetCollection().remove(reservetCollectionReservet);
                    oldIdClientOfReservetCollectionReservet = em.merge(oldIdClientOfReservetCollectionReservet);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clients clients) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clients persistentClients = em.find(Clients.class, clients.getIdClient());
            Collection<Reservet> reservetCollectionOld = persistentClients.getReservetCollection();
            Collection<Reservet> reservetCollectionNew = clients.getReservetCollection();
            Collection<Reservet> attachedReservetCollectionNew = new ArrayList<Reservet>();
            for (Reservet reservetCollectionNewReservetToAttach : reservetCollectionNew) {
                reservetCollectionNewReservetToAttach = em.getReference(reservetCollectionNewReservetToAttach.getClass(), reservetCollectionNewReservetToAttach.getIdReserv());
                attachedReservetCollectionNew.add(reservetCollectionNewReservetToAttach);
            }
            reservetCollectionNew = attachedReservetCollectionNew;
            clients.setReservetCollection(reservetCollectionNew);
            clients = em.merge(clients);
            for (Reservet reservetCollectionOldReservet : reservetCollectionOld) {
                if (!reservetCollectionNew.contains(reservetCollectionOldReservet)) {
                    reservetCollectionOldReservet.setIdClient(null);
                    reservetCollectionOldReservet = em.merge(reservetCollectionOldReservet);
                }
            }
            for (Reservet reservetCollectionNewReservet : reservetCollectionNew) {
                if (!reservetCollectionOld.contains(reservetCollectionNewReservet)) {
                    Clients oldIdClientOfReservetCollectionNewReservet = reservetCollectionNewReservet.getIdClient();
                    reservetCollectionNewReservet.setIdClient(clients);
                    reservetCollectionNewReservet = em.merge(reservetCollectionNewReservet);
                    if (oldIdClientOfReservetCollectionNewReservet != null && !oldIdClientOfReservetCollectionNewReservet.equals(clients)) {
                        oldIdClientOfReservetCollectionNewReservet.getReservetCollection().remove(reservetCollectionNewReservet);
                        oldIdClientOfReservetCollectionNewReservet = em.merge(oldIdClientOfReservetCollectionNewReservet);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clients.getIdClient();
                if (findClients(id) == null) {
                    throw new NonexistentEntityException("The clients with id " + id + " no longer exists.");
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
            Clients clients;
            try {
                clients = em.getReference(Clients.class, id);
                clients.getIdClient();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clients with id " + id + " no longer exists.", enfe);
            }
            Collection<Reservet> reservetCollection = clients.getReservetCollection();
            for (Reservet reservetCollectionReservet : reservetCollection) {
                reservetCollectionReservet.setIdClient(null);
                reservetCollectionReservet = em.merge(reservetCollectionReservet);
            }
            em.remove(clients);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clients> findClientsEntities() {
        return findClientsEntities(true, -1, -1);
    }

    public List<Clients> findClientsEntities(int maxResults, int firstResult) {
        return findClientsEntities(false, maxResults, firstResult);
    }

    private List<Clients> findClientsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clients.class));
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

    public Clients findClients(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clients.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clients> rt = cq.from(Clients.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
