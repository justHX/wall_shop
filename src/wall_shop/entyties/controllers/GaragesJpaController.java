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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import wall_shop.entyties.Garages;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author hulk-
 */
public class GaragesJpaController implements Serializable {

    public GaragesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Garages garages) {
        if (garages.getInfoGaragesCollection() == null) {
            garages.setInfoGaragesCollection(new ArrayList<InfoGarages>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<InfoGarages> attachedInfoGaragesCollection = new ArrayList<InfoGarages>();
            for (InfoGarages infoGaragesCollectionInfoGaragesToAttach : garages.getInfoGaragesCollection()) {
                infoGaragesCollectionInfoGaragesToAttach = em.getReference(infoGaragesCollectionInfoGaragesToAttach.getClass(), infoGaragesCollectionInfoGaragesToAttach.getIdinfGar());
                attachedInfoGaragesCollection.add(infoGaragesCollectionInfoGaragesToAttach);
            }
            garages.setInfoGaragesCollection(attachedInfoGaragesCollection);
            em.persist(garages);
            for (InfoGarages infoGaragesCollectionInfoGarages : garages.getInfoGaragesCollection()) {
                Garages oldIdGarageOfInfoGaragesCollectionInfoGarages = infoGaragesCollectionInfoGarages.getIdGarage();
                infoGaragesCollectionInfoGarages.setIdGarage(garages);
                infoGaragesCollectionInfoGarages = em.merge(infoGaragesCollectionInfoGarages);
                if (oldIdGarageOfInfoGaragesCollectionInfoGarages != null) {
                    oldIdGarageOfInfoGaragesCollectionInfoGarages.getInfoGaragesCollection().remove(infoGaragesCollectionInfoGarages);
                    oldIdGarageOfInfoGaragesCollectionInfoGarages = em.merge(oldIdGarageOfInfoGaragesCollectionInfoGarages);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Garages garages) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Garages persistentGarages = em.find(Garages.class, garages.getIdGarage());
            Collection<InfoGarages> infoGaragesCollectionOld = persistentGarages.getInfoGaragesCollection();
            Collection<InfoGarages> infoGaragesCollectionNew = garages.getInfoGaragesCollection();
            Collection<InfoGarages> attachedInfoGaragesCollectionNew = new ArrayList<InfoGarages>();
            for (InfoGarages infoGaragesCollectionNewInfoGaragesToAttach : infoGaragesCollectionNew) {
                infoGaragesCollectionNewInfoGaragesToAttach = em.getReference(infoGaragesCollectionNewInfoGaragesToAttach.getClass(), infoGaragesCollectionNewInfoGaragesToAttach.getIdinfGar());
                attachedInfoGaragesCollectionNew.add(infoGaragesCollectionNewInfoGaragesToAttach);
            }
            infoGaragesCollectionNew = attachedInfoGaragesCollectionNew;
            garages.setInfoGaragesCollection(infoGaragesCollectionNew);
            garages = em.merge(garages);
            for (InfoGarages infoGaragesCollectionOldInfoGarages : infoGaragesCollectionOld) {
                if (!infoGaragesCollectionNew.contains(infoGaragesCollectionOldInfoGarages)) {
                    infoGaragesCollectionOldInfoGarages.setIdGarage(null);
                    infoGaragesCollectionOldInfoGarages = em.merge(infoGaragesCollectionOldInfoGarages);
                }
            }
            for (InfoGarages infoGaragesCollectionNewInfoGarages : infoGaragesCollectionNew) {
                if (!infoGaragesCollectionOld.contains(infoGaragesCollectionNewInfoGarages)) {
                    Garages oldIdGarageOfInfoGaragesCollectionNewInfoGarages = infoGaragesCollectionNewInfoGarages.getIdGarage();
                    infoGaragesCollectionNewInfoGarages.setIdGarage(garages);
                    infoGaragesCollectionNewInfoGarages = em.merge(infoGaragesCollectionNewInfoGarages);
                    if (oldIdGarageOfInfoGaragesCollectionNewInfoGarages != null && !oldIdGarageOfInfoGaragesCollectionNewInfoGarages.equals(garages)) {
                        oldIdGarageOfInfoGaragesCollectionNewInfoGarages.getInfoGaragesCollection().remove(infoGaragesCollectionNewInfoGarages);
                        oldIdGarageOfInfoGaragesCollectionNewInfoGarages = em.merge(oldIdGarageOfInfoGaragesCollectionNewInfoGarages);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = garages.getIdGarage();
                if (findGarages(id) == null) {
                    throw new NonexistentEntityException("The garages with id " + id + " no longer exists.");
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
            Garages garages;
            try {
                garages = em.getReference(Garages.class, id);
                garages.getIdGarage();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The garages with id " + id + " no longer exists.", enfe);
            }
            Collection<InfoGarages> infoGaragesCollection = garages.getInfoGaragesCollection();
            for (InfoGarages infoGaragesCollectionInfoGarages : infoGaragesCollection) {
                infoGaragesCollectionInfoGarages.setIdGarage(null);
                infoGaragesCollectionInfoGarages = em.merge(infoGaragesCollectionInfoGarages);
            }
            em.remove(garages);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Garages> findGaragesEntities() {
        return findGaragesEntities(true, -1, -1);
    }

    public List<Garages> findGaragesEntities(int maxResults, int firstResult) {
        return findGaragesEntities(false, maxResults, firstResult);
    }

    private List<Garages> findGaragesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Garages.class));
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

    public Garages findGarages(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Garages.class, id);
        } finally {
            em.close();
        }
    }

    public int getGaragesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Garages> rt = cq.from(Garages.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
