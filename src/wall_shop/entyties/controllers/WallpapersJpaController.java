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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import wall_shop.entyties.Wallpapers;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author hulk-
 */
public class WallpapersJpaController implements Serializable {

    public WallpapersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Wallpapers wallpapers) {
        if (wallpapers.getProviderCollection() == null) {
            wallpapers.setProviderCollection(new ArrayList<Provider>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Provider> attachedProviderCollection = new ArrayList<Provider>();
            for (Provider providerCollectionProviderToAttach : wallpapers.getProviderCollection()) {
                providerCollectionProviderToAttach = em.getReference(providerCollectionProviderToAttach.getClass(), providerCollectionProviderToAttach.getIdProvider());
                attachedProviderCollection.add(providerCollectionProviderToAttach);
            }
            wallpapers.setProviderCollection(attachedProviderCollection);
            em.persist(wallpapers);
            for (Provider providerCollectionProvider : wallpapers.getProviderCollection()) {
                Wallpapers oldIdWallOfProviderCollectionProvider = providerCollectionProvider.getIdWall();
                providerCollectionProvider.setIdWall(wallpapers);
                providerCollectionProvider = em.merge(providerCollectionProvider);
                if (oldIdWallOfProviderCollectionProvider != null) {
                    oldIdWallOfProviderCollectionProvider.getProviderCollection().remove(providerCollectionProvider);
                    oldIdWallOfProviderCollectionProvider = em.merge(oldIdWallOfProviderCollectionProvider);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Wallpapers wallpapers) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Wallpapers persistentWallpapers = em.find(Wallpapers.class, wallpapers.getIdWall());
            Collection<Provider> providerCollectionOld = persistentWallpapers.getProviderCollection();
            Collection<Provider> providerCollectionNew = wallpapers.getProviderCollection();
            Collection<Provider> attachedProviderCollectionNew = new ArrayList<Provider>();
            for (Provider providerCollectionNewProviderToAttach : providerCollectionNew) {
                providerCollectionNewProviderToAttach = em.getReference(providerCollectionNewProviderToAttach.getClass(), providerCollectionNewProviderToAttach.getIdProvider());
                attachedProviderCollectionNew.add(providerCollectionNewProviderToAttach);
            }
            providerCollectionNew = attachedProviderCollectionNew;
            wallpapers.setProviderCollection(providerCollectionNew);
            wallpapers = em.merge(wallpapers);
            for (Provider providerCollectionOldProvider : providerCollectionOld) {
                if (!providerCollectionNew.contains(providerCollectionOldProvider)) {
                    providerCollectionOldProvider.setIdWall(null);
                    providerCollectionOldProvider = em.merge(providerCollectionOldProvider);
                }
            }
            for (Provider providerCollectionNewProvider : providerCollectionNew) {
                if (!providerCollectionOld.contains(providerCollectionNewProvider)) {
                    Wallpapers oldIdWallOfProviderCollectionNewProvider = providerCollectionNewProvider.getIdWall();
                    providerCollectionNewProvider.setIdWall(wallpapers);
                    providerCollectionNewProvider = em.merge(providerCollectionNewProvider);
                    if (oldIdWallOfProviderCollectionNewProvider != null && !oldIdWallOfProviderCollectionNewProvider.equals(wallpapers)) {
                        oldIdWallOfProviderCollectionNewProvider.getProviderCollection().remove(providerCollectionNewProvider);
                        oldIdWallOfProviderCollectionNewProvider = em.merge(oldIdWallOfProviderCollectionNewProvider);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = wallpapers.getIdWall();
                if (findWallpapers(id) == null) {
                    throw new NonexistentEntityException("The wallpapers with id " + id + " no longer exists.");
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
            Wallpapers wallpapers;
            try {
                wallpapers = em.getReference(Wallpapers.class, id);
                wallpapers.getIdWall();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The wallpapers with id " + id + " no longer exists.", enfe);
            }
            Collection<Provider> providerCollection = wallpapers.getProviderCollection();
            for (Provider providerCollectionProvider : providerCollection) {
                providerCollectionProvider.setIdWall(null);
                providerCollectionProvider = em.merge(providerCollectionProvider);
            }
            em.remove(wallpapers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Wallpapers> findWallpapersEntities() {
        return findWallpapersEntities(true, -1, -1);
    }

    public List<Wallpapers> findWallpapersEntities(int maxResults, int firstResult) {
        return findWallpapersEntities(false, maxResults, firstResult);
    }

    private List<Wallpapers> findWallpapersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Wallpapers.class));
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

    public Wallpapers findWallpapers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Wallpapers.class, id);
        } finally {
            em.close();
        }
    }

    public int getWallpapersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Wallpapers> rt = cq.from(Wallpapers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    

}
