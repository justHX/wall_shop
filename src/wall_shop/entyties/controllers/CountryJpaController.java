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
import wall_shop.entyties.Country;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author hulk-
 */
public class CountryJpaController implements Serializable {

    public CountryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Country country) {
        if (country.getProviderCollection() == null) {
            country.setProviderCollection(new ArrayList<Provider>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Provider> attachedProviderCollection = new ArrayList<Provider>();
            for (Provider providerCollectionProviderToAttach : country.getProviderCollection()) {
                providerCollectionProviderToAttach = em.getReference(providerCollectionProviderToAttach.getClass(), providerCollectionProviderToAttach.getIdProvider());
                attachedProviderCollection.add(providerCollectionProviderToAttach);
            }
            country.setProviderCollection(attachedProviderCollection);
            em.persist(country);
            for (Provider providerCollectionProvider : country.getProviderCollection()) {
                Country oldIdCountryOfProviderCollectionProvider = providerCollectionProvider.getIdCountry();
                providerCollectionProvider.setIdCountry(country);
                providerCollectionProvider = em.merge(providerCollectionProvider);
                if (oldIdCountryOfProviderCollectionProvider != null) {
                    oldIdCountryOfProviderCollectionProvider.getProviderCollection().remove(providerCollectionProvider);
                    oldIdCountryOfProviderCollectionProvider = em.merge(oldIdCountryOfProviderCollectionProvider);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Country country) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Country persistentCountry = em.find(Country.class, country.getIdCountry());
            Collection<Provider> providerCollectionOld = persistentCountry.getProviderCollection();
            Collection<Provider> providerCollectionNew = country.getProviderCollection();
            Collection<Provider> attachedProviderCollectionNew = new ArrayList<Provider>();
            for (Provider providerCollectionNewProviderToAttach : providerCollectionNew) {
                providerCollectionNewProviderToAttach = em.getReference(providerCollectionNewProviderToAttach.getClass(), providerCollectionNewProviderToAttach.getIdProvider());
                attachedProviderCollectionNew.add(providerCollectionNewProviderToAttach);
            }
            providerCollectionNew = attachedProviderCollectionNew;
            country.setProviderCollection(providerCollectionNew);
            country = em.merge(country);
            for (Provider providerCollectionOldProvider : providerCollectionOld) {
                if (!providerCollectionNew.contains(providerCollectionOldProvider)) {
                    providerCollectionOldProvider.setIdCountry(null);
                    providerCollectionOldProvider = em.merge(providerCollectionOldProvider);
                }
            }
            for (Provider providerCollectionNewProvider : providerCollectionNew) {
                if (!providerCollectionOld.contains(providerCollectionNewProvider)) {
                    Country oldIdCountryOfProviderCollectionNewProvider = providerCollectionNewProvider.getIdCountry();
                    providerCollectionNewProvider.setIdCountry(country);
                    providerCollectionNewProvider = em.merge(providerCollectionNewProvider);
                    if (oldIdCountryOfProviderCollectionNewProvider != null && !oldIdCountryOfProviderCollectionNewProvider.equals(country)) {
                        oldIdCountryOfProviderCollectionNewProvider.getProviderCollection().remove(providerCollectionNewProvider);
                        oldIdCountryOfProviderCollectionNewProvider = em.merge(oldIdCountryOfProviderCollectionNewProvider);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = country.getIdCountry();
                if (findCountry(id) == null) {
                    throw new NonexistentEntityException("The country with id " + id + " no longer exists.");
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
            Country country;
            try {
                country = em.getReference(Country.class, id);
                country.getIdCountry();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The country with id " + id + " no longer exists.", enfe);
            }
            Collection<Provider> providerCollection = country.getProviderCollection();
            for (Provider providerCollectionProvider : providerCollection) {
                providerCollectionProvider.setIdCountry(null);
                providerCollectionProvider = em.merge(providerCollectionProvider);
            }
            em.remove(country);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Country> findCountryEntities() {
        return findCountryEntities(true, -1, -1);
    }

    public List<Country> findCountryEntities(int maxResults, int firstResult) {
        return findCountryEntities(false, maxResults, firstResult);
    }

    private List<Country> findCountryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Country.class));
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

    public Country findCountry(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Country.class, id);
        } finally {
            em.close();
        }
    }

    public int getCountryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Country> rt = cq.from(Country.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
