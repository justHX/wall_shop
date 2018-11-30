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
import wall_shop.entyties.Color;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author hulk-
 */
public class ColorJpaController implements Serializable {

    public ColorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Color color) {
        if (color.getProviderCollection() == null) {
            color.setProviderCollection(new ArrayList<Provider>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Provider> attachedProviderCollection = new ArrayList<Provider>();
            for (Provider providerCollectionProviderToAttach : color.getProviderCollection()) {
                providerCollectionProviderToAttach = em.getReference(providerCollectionProviderToAttach.getClass(), providerCollectionProviderToAttach.getIdProvider());
                attachedProviderCollection.add(providerCollectionProviderToAttach);
            }
            color.setProviderCollection(attachedProviderCollection);
            em.persist(color);
            for (Provider providerCollectionProvider : color.getProviderCollection()) {
                Color oldIdColorOfProviderCollectionProvider = providerCollectionProvider.getIdColor();
                providerCollectionProvider.setIdColor(color);
                providerCollectionProvider = em.merge(providerCollectionProvider);
                if (oldIdColorOfProviderCollectionProvider != null) {
                    oldIdColorOfProviderCollectionProvider.getProviderCollection().remove(providerCollectionProvider);
                    oldIdColorOfProviderCollectionProvider = em.merge(oldIdColorOfProviderCollectionProvider);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Color color) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Color persistentColor = em.find(Color.class, color.getIdColor());
            Collection<Provider> providerCollectionOld = persistentColor.getProviderCollection();
            Collection<Provider> providerCollectionNew = color.getProviderCollection();
            Collection<Provider> attachedProviderCollectionNew = new ArrayList<Provider>();
            for (Provider providerCollectionNewProviderToAttach : providerCollectionNew) {
                providerCollectionNewProviderToAttach = em.getReference(providerCollectionNewProviderToAttach.getClass(), providerCollectionNewProviderToAttach.getIdProvider());
                attachedProviderCollectionNew.add(providerCollectionNewProviderToAttach);
            }
            providerCollectionNew = attachedProviderCollectionNew;
            color.setProviderCollection(providerCollectionNew);
            color = em.merge(color);
            for (Provider providerCollectionOldProvider : providerCollectionOld) {
                if (!providerCollectionNew.contains(providerCollectionOldProvider)) {
                    providerCollectionOldProvider.setIdColor(null);
                    providerCollectionOldProvider = em.merge(providerCollectionOldProvider);
                }
            }
            for (Provider providerCollectionNewProvider : providerCollectionNew) {
                if (!providerCollectionOld.contains(providerCollectionNewProvider)) {
                    Color oldIdColorOfProviderCollectionNewProvider = providerCollectionNewProvider.getIdColor();
                    providerCollectionNewProvider.setIdColor(color);
                    providerCollectionNewProvider = em.merge(providerCollectionNewProvider);
                    if (oldIdColorOfProviderCollectionNewProvider != null && !oldIdColorOfProviderCollectionNewProvider.equals(color)) {
                        oldIdColorOfProviderCollectionNewProvider.getProviderCollection().remove(providerCollectionNewProvider);
                        oldIdColorOfProviderCollectionNewProvider = em.merge(oldIdColorOfProviderCollectionNewProvider);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = color.getIdColor();
                if (findColor(id) == null) {
                    throw new NonexistentEntityException("The color with id " + id + " no longer exists.");
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
            Color color;
            try {
                color = em.getReference(Color.class, id);
                color.getIdColor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The color with id " + id + " no longer exists.", enfe);
            }
            Collection<Provider> providerCollection = color.getProviderCollection();
            for (Provider providerCollectionProvider : providerCollection) {
                providerCollectionProvider.setIdColor(null);
                providerCollectionProvider = em.merge(providerCollectionProvider);
            }
            em.remove(color);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Color> findColorEntities() {
        return findColorEntities(true, -1, -1);
    }

    public List<Color> findColorEntities(int maxResults, int firstResult) {
        return findColorEntities(false, maxResults, firstResult);
    }

    private List<Color> findColorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Color.class));
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

    public Color findColor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Color.class, id);
        } finally {
            em.close();
        }
    }

    public int getColorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Color> rt = cq.from(Color.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
