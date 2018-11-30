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
import wall_shop.entyties.Provider;
import wall_shop.entyties.Garages;
import wall_shop.entyties.Reservet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import wall_shop.entyties.InfoGarages;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author hulk-
 */
public class InfoGaragesJpaController implements Serializable {

    public InfoGaragesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InfoGarages infoGarages) {
        if (infoGarages.getReservetCollection() == null) {
            infoGarages.setReservetCollection(new ArrayList<Reservet>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provider idProvider = infoGarages.getIdProvider();
            if (idProvider != null) {
                idProvider = em.getReference(idProvider.getClass(), idProvider.getIdProvider());
                infoGarages.setIdProvider(idProvider);
            }
            Garages idGarage = infoGarages.getIdGarage();
            if (idGarage != null) {
                idGarage = em.getReference(idGarage.getClass(), idGarage.getIdGarage());
                infoGarages.setIdGarage(idGarage);
            }
            Collection<Reservet> attachedReservetCollection = new ArrayList<Reservet>();
            for (Reservet reservetCollectionReservetToAttach : infoGarages.getReservetCollection()) {
                reservetCollectionReservetToAttach = em.getReference(reservetCollectionReservetToAttach.getClass(), reservetCollectionReservetToAttach.getIdReserv());
                attachedReservetCollection.add(reservetCollectionReservetToAttach);
            }
            infoGarages.setReservetCollection(attachedReservetCollection);
            em.persist(infoGarages);
            if (idProvider != null) {
                idProvider.getInfoGaragesCollection().add(infoGarages);
                idProvider = em.merge(idProvider);
            }
            if (idGarage != null) {
                idGarage.getInfoGaragesCollection().add(infoGarages);
                idGarage = em.merge(idGarage);
            }
            for (Reservet reservetCollectionReservet : infoGarages.getReservetCollection()) {
                InfoGarages oldIdinfGarOfReservetCollectionReservet = reservetCollectionReservet.getIdinfGar();
                reservetCollectionReservet.setIdinfGar(infoGarages);
                reservetCollectionReservet = em.merge(reservetCollectionReservet);
                if (oldIdinfGarOfReservetCollectionReservet != null) {
                    oldIdinfGarOfReservetCollectionReservet.getReservetCollection().remove(reservetCollectionReservet);
                    oldIdinfGarOfReservetCollectionReservet = em.merge(oldIdinfGarOfReservetCollectionReservet);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InfoGarages infoGarages) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InfoGarages persistentInfoGarages = em.find(InfoGarages.class, infoGarages.getIdinfGar());
            Provider idProviderOld = persistentInfoGarages.getIdProvider();
            Provider idProviderNew = infoGarages.getIdProvider();
            Garages idGarageOld = persistentInfoGarages.getIdGarage();
            Garages idGarageNew = infoGarages.getIdGarage();
            Collection<Reservet> reservetCollectionOld = persistentInfoGarages.getReservetCollection();
            Collection<Reservet> reservetCollectionNew = infoGarages.getReservetCollection();
            if (idProviderNew != null) {
                idProviderNew = em.getReference(idProviderNew.getClass(), idProviderNew.getIdProvider());
                infoGarages.setIdProvider(idProviderNew);
            }
            if (idGarageNew != null) {
                idGarageNew = em.getReference(idGarageNew.getClass(), idGarageNew.getIdGarage());
                infoGarages.setIdGarage(idGarageNew);
            }
            Collection<Reservet> attachedReservetCollectionNew = new ArrayList<Reservet>();
            for (Reservet reservetCollectionNewReservetToAttach : reservetCollectionNew) {
                reservetCollectionNewReservetToAttach = em.getReference(reservetCollectionNewReservetToAttach.getClass(), reservetCollectionNewReservetToAttach.getIdReserv());
                attachedReservetCollectionNew.add(reservetCollectionNewReservetToAttach);
            }
            reservetCollectionNew = attachedReservetCollectionNew;
            infoGarages.setReservetCollection(reservetCollectionNew);
            infoGarages = em.merge(infoGarages);
            if (idProviderOld != null && !idProviderOld.equals(idProviderNew)) {
                idProviderOld.getInfoGaragesCollection().remove(infoGarages);
                idProviderOld = em.merge(idProviderOld);
            }
            if (idProviderNew != null && !idProviderNew.equals(idProviderOld)) {
                idProviderNew.getInfoGaragesCollection().add(infoGarages);
                idProviderNew = em.merge(idProviderNew);
            }
            if (idGarageOld != null && !idGarageOld.equals(idGarageNew)) {
                idGarageOld.getInfoGaragesCollection().remove(infoGarages);
                idGarageOld = em.merge(idGarageOld);
            }
            if (idGarageNew != null && !idGarageNew.equals(idGarageOld)) {
                idGarageNew.getInfoGaragesCollection().add(infoGarages);
                idGarageNew = em.merge(idGarageNew);
            }
            for (Reservet reservetCollectionOldReservet : reservetCollectionOld) {
                if (!reservetCollectionNew.contains(reservetCollectionOldReservet)) {
                    reservetCollectionOldReservet.setIdinfGar(null);
                    reservetCollectionOldReservet = em.merge(reservetCollectionOldReservet);
                }
            }
            for (Reservet reservetCollectionNewReservet : reservetCollectionNew) {
                if (!reservetCollectionOld.contains(reservetCollectionNewReservet)) {
                    InfoGarages oldIdinfGarOfReservetCollectionNewReservet = reservetCollectionNewReservet.getIdinfGar();
                    reservetCollectionNewReservet.setIdinfGar(infoGarages);
                    reservetCollectionNewReservet = em.merge(reservetCollectionNewReservet);
                    if (oldIdinfGarOfReservetCollectionNewReservet != null && !oldIdinfGarOfReservetCollectionNewReservet.equals(infoGarages)) {
                        oldIdinfGarOfReservetCollectionNewReservet.getReservetCollection().remove(reservetCollectionNewReservet);
                        oldIdinfGarOfReservetCollectionNewReservet = em.merge(oldIdinfGarOfReservetCollectionNewReservet);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = infoGarages.getIdinfGar();
                if (findInfoGarages(id) == null) {
                    throw new NonexistentEntityException("The infoGarages with id " + id + " no longer exists.");
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
            InfoGarages infoGarages;
            try {
                infoGarages = em.getReference(InfoGarages.class, id);
                infoGarages.getIdinfGar();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The infoGarages with id " + id + " no longer exists.", enfe);
            }
            Provider idProvider = infoGarages.getIdProvider();
            if (idProvider != null) {
                idProvider.getInfoGaragesCollection().remove(infoGarages);
                idProvider = em.merge(idProvider);
            }
            Garages idGarage = infoGarages.getIdGarage();
            if (idGarage != null) {
                idGarage.getInfoGaragesCollection().remove(infoGarages);
                idGarage = em.merge(idGarage);
            }
            Collection<Reservet> reservetCollection = infoGarages.getReservetCollection();
            for (Reservet reservetCollectionReservet : reservetCollection) {
                reservetCollectionReservet.setIdinfGar(null);
                reservetCollectionReservet = em.merge(reservetCollectionReservet);
            }
            em.remove(infoGarages);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InfoGarages> findInfoGaragesEntities() {
        return findInfoGaragesEntities(true, -1, -1);
    }

    public List<InfoGarages> findInfoGaragesEntities(int maxResults, int firstResult) {
        return findInfoGaragesEntities(false, maxResults, firstResult);
    }

    private List<InfoGarages> findInfoGaragesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InfoGarages.class));
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

    public InfoGarages findInfoGarages(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InfoGarages.class, id);
        } finally {
            em.close();
        }
    }

    public int getInfoGaragesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InfoGarages> rt = cq.from(InfoGarages.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
