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
import wall_shop.entyties.Party;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author hulk-
 */
public class PartyJpaController implements Serializable {

    public PartyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Party party) {
        if (party.getProviderCollection() == null) {
            party.setProviderCollection(new ArrayList<Provider>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Provider> attachedProviderCollection = new ArrayList<Provider>();
            for (Provider providerCollectionProviderToAttach : party.getProviderCollection()) {
                providerCollectionProviderToAttach = em.getReference(providerCollectionProviderToAttach.getClass(), providerCollectionProviderToAttach.getIdProvider());
                attachedProviderCollection.add(providerCollectionProviderToAttach);
            }
            party.setProviderCollection(attachedProviderCollection);
            em.persist(party);
            for (Provider providerCollectionProvider : party.getProviderCollection()) {
                Party oldIdPartyOfProviderCollectionProvider = providerCollectionProvider.getIdParty();
                providerCollectionProvider.setIdParty(party);
                providerCollectionProvider = em.merge(providerCollectionProvider);
                if (oldIdPartyOfProviderCollectionProvider != null) {
                    oldIdPartyOfProviderCollectionProvider.getProviderCollection().remove(providerCollectionProvider);
                    oldIdPartyOfProviderCollectionProvider = em.merge(oldIdPartyOfProviderCollectionProvider);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Party party) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Party persistentParty = em.find(Party.class, party.getIdParty());
            Collection<Provider> providerCollectionOld = persistentParty.getProviderCollection();
            Collection<Provider> providerCollectionNew = party.getProviderCollection();
            Collection<Provider> attachedProviderCollectionNew = new ArrayList<Provider>();
            for (Provider providerCollectionNewProviderToAttach : providerCollectionNew) {
                providerCollectionNewProviderToAttach = em.getReference(providerCollectionNewProviderToAttach.getClass(), providerCollectionNewProviderToAttach.getIdProvider());
                attachedProviderCollectionNew.add(providerCollectionNewProviderToAttach);
            }
            providerCollectionNew = attachedProviderCollectionNew;
            party.setProviderCollection(providerCollectionNew);
            party = em.merge(party);
            for (Provider providerCollectionOldProvider : providerCollectionOld) {
                if (!providerCollectionNew.contains(providerCollectionOldProvider)) {
                    providerCollectionOldProvider.setIdParty(null);
                    providerCollectionOldProvider = em.merge(providerCollectionOldProvider);
                }
            }
            for (Provider providerCollectionNewProvider : providerCollectionNew) {
                if (!providerCollectionOld.contains(providerCollectionNewProvider)) {
                    Party oldIdPartyOfProviderCollectionNewProvider = providerCollectionNewProvider.getIdParty();
                    providerCollectionNewProvider.setIdParty(party);
                    providerCollectionNewProvider = em.merge(providerCollectionNewProvider);
                    if (oldIdPartyOfProviderCollectionNewProvider != null && !oldIdPartyOfProviderCollectionNewProvider.equals(party)) {
                        oldIdPartyOfProviderCollectionNewProvider.getProviderCollection().remove(providerCollectionNewProvider);
                        oldIdPartyOfProviderCollectionNewProvider = em.merge(oldIdPartyOfProviderCollectionNewProvider);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = party.getIdParty();
                if (findParty(id) == null) {
                    throw new NonexistentEntityException("The party with id " + id + " no longer exists.");
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
            Party party;
            try {
                party = em.getReference(Party.class, id);
                party.getIdParty();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The party with id " + id + " no longer exists.", enfe);
            }
            Collection<Provider> providerCollection = party.getProviderCollection();
            for (Provider providerCollectionProvider : providerCollection) {
                providerCollectionProvider.setIdParty(null);
                providerCollectionProvider = em.merge(providerCollectionProvider);
            }
            em.remove(party);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Party> findPartyEntities() {
        return findPartyEntities(true, -1, -1);
    }

    public List<Party> findPartyEntities(int maxResults, int firstResult) {
        return findPartyEntities(false, maxResults, firstResult);
    }

    private List<Party> findPartyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Party.class));
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

    public Party findParty(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Party.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Party> rt = cq.from(Party.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
