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
import wall_shop.entyties.Companys;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author hulk-
 */
public class CompanysJpaController implements Serializable {

    public CompanysJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Companys companys) {
        if (companys.getProviderCollection() == null) {
            companys.setProviderCollection(new ArrayList<Provider>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Provider> attachedProviderCollection = new ArrayList<Provider>();
            for (Provider providerCollectionProviderToAttach : companys.getProviderCollection()) {
                providerCollectionProviderToAttach = em.getReference(providerCollectionProviderToAttach.getClass(), providerCollectionProviderToAttach.getIdProvider());
                attachedProviderCollection.add(providerCollectionProviderToAttach);
            }
            companys.setProviderCollection(attachedProviderCollection);
            em.persist(companys);
            for (Provider providerCollectionProvider : companys.getProviderCollection()) {
                Companys oldIdCompanyOfProviderCollectionProvider = providerCollectionProvider.getIdCompany();
                providerCollectionProvider.setIdCompany(companys);
                providerCollectionProvider = em.merge(providerCollectionProvider);
                if (oldIdCompanyOfProviderCollectionProvider != null) {
                    oldIdCompanyOfProviderCollectionProvider.getProviderCollection().remove(providerCollectionProvider);
                    oldIdCompanyOfProviderCollectionProvider = em.merge(oldIdCompanyOfProviderCollectionProvider);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Companys companys) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Companys persistentCompanys = em.find(Companys.class, companys.getIdCompany());
            Collection<Provider> providerCollectionOld = persistentCompanys.getProviderCollection();
            Collection<Provider> providerCollectionNew = companys.getProviderCollection();
            Collection<Provider> attachedProviderCollectionNew = new ArrayList<Provider>();
            for (Provider providerCollectionNewProviderToAttach : providerCollectionNew) {
                providerCollectionNewProviderToAttach = em.getReference(providerCollectionNewProviderToAttach.getClass(), providerCollectionNewProviderToAttach.getIdProvider());
                attachedProviderCollectionNew.add(providerCollectionNewProviderToAttach);
            }
            providerCollectionNew = attachedProviderCollectionNew;
            companys.setProviderCollection(providerCollectionNew);
            companys = em.merge(companys);
            for (Provider providerCollectionOldProvider : providerCollectionOld) {
                if (!providerCollectionNew.contains(providerCollectionOldProvider)) {
                    providerCollectionOldProvider.setIdCompany(null);
                    providerCollectionOldProvider = em.merge(providerCollectionOldProvider);
                }
            }
            for (Provider providerCollectionNewProvider : providerCollectionNew) {
                if (!providerCollectionOld.contains(providerCollectionNewProvider)) {
                    Companys oldIdCompanyOfProviderCollectionNewProvider = providerCollectionNewProvider.getIdCompany();
                    providerCollectionNewProvider.setIdCompany(companys);
                    providerCollectionNewProvider = em.merge(providerCollectionNewProvider);
                    if (oldIdCompanyOfProviderCollectionNewProvider != null && !oldIdCompanyOfProviderCollectionNewProvider.equals(companys)) {
                        oldIdCompanyOfProviderCollectionNewProvider.getProviderCollection().remove(providerCollectionNewProvider);
                        oldIdCompanyOfProviderCollectionNewProvider = em.merge(oldIdCompanyOfProviderCollectionNewProvider);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = companys.getIdCompany();
                if (findCompanys(id) == null) {
                    throw new NonexistentEntityException("The companys with id " + id + " no longer exists.");
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
            Companys companys;
            try {
                companys = em.getReference(Companys.class, id);
                companys.getIdCompany();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The companys with id " + id + " no longer exists.", enfe);
            }
            Collection<Provider> providerCollection = companys.getProviderCollection();
            for (Provider providerCollectionProvider : providerCollection) {
                providerCollectionProvider.setIdCompany(null);
                providerCollectionProvider = em.merge(providerCollectionProvider);
            }
            em.remove(companys);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Companys> findCompanysEntities() {
        return findCompanysEntities(true, -1, -1);
    }

    public List<Companys> findCompanysEntities(int maxResults, int firstResult) {
        return findCompanysEntities(false, maxResults, firstResult);
    }

    private List<Companys> findCompanysEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Companys.class));
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

    public Companys findCompanys(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Companys.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompanysCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Companys> rt = cq.from(Companys.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
