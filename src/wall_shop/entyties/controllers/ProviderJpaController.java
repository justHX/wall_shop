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
import wall_shop.entyties.Party;
import wall_shop.entyties.Color;
import wall_shop.entyties.Companys;
import wall_shop.entyties.Country;
import wall_shop.entyties.Wallpapers;
import wall_shop.entyties.InfoGarages;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import wall_shop.entyties.Provider;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 *
 * @author hulk-
 */
public class ProviderJpaController implements Serializable {

    public ProviderJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Provider provider) {
        if (provider.getInfoGaragesCollection() == null) {
            provider.setInfoGaragesCollection(new ArrayList<InfoGarages>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Party idParty = provider.getIdParty();
            if (idParty != null) {
                idParty = em.getReference(idParty.getClass(), idParty.getIdParty());
                provider.setIdParty(idParty);
            }
            Color idColor = provider.getIdColor();
            if (idColor != null) {
                idColor = em.getReference(idColor.getClass(), idColor.getIdColor());
                provider.setIdColor(idColor);
            }
            Companys idCompany = provider.getIdCompany();
            if (idCompany != null) {
                idCompany = em.getReference(idCompany.getClass(), idCompany.getIdCompany());
                provider.setIdCompany(idCompany);
            }
            Country idCountry = provider.getIdCountry();
            if (idCountry != null) {
                idCountry = em.getReference(idCountry.getClass(), idCountry.getIdCountry());
                provider.setIdCountry(idCountry);
            }
            Wallpapers idWall = provider.getIdWall();
            if (idWall != null) {
                idWall = em.getReference(idWall.getClass(), idWall.getIdWall());
                provider.setIdWall(idWall);
            }
            Collection<InfoGarages> attachedInfoGaragesCollection = new ArrayList<InfoGarages>();
            for (InfoGarages infoGaragesCollectionInfoGaragesToAttach : provider.getInfoGaragesCollection()) {
                infoGaragesCollectionInfoGaragesToAttach = em.getReference(infoGaragesCollectionInfoGaragesToAttach.getClass(), infoGaragesCollectionInfoGaragesToAttach.getIdinfGar());
                attachedInfoGaragesCollection.add(infoGaragesCollectionInfoGaragesToAttach);
            }
            provider.setInfoGaragesCollection(attachedInfoGaragesCollection);
            em.persist(provider);
            if (idParty != null) {
                idParty.getProviderCollection().add(provider);
                idParty = em.merge(idParty);
            }
            if (idColor != null) {
                idColor.getProviderCollection().add(provider);
                idColor = em.merge(idColor);
            }
            if (idCompany != null) {
                idCompany.getProviderCollection().add(provider);
                idCompany = em.merge(idCompany);
            }
            if (idCountry != null) {
                idCountry.getProviderCollection().add(provider);
                idCountry = em.merge(idCountry);
            }
            if (idWall != null) {
                idWall.getProviderCollection().add(provider);
                idWall = em.merge(idWall);
            }
            for (InfoGarages infoGaragesCollectionInfoGarages : provider.getInfoGaragesCollection()) {
                Provider oldIdProviderOfInfoGaragesCollectionInfoGarages = infoGaragesCollectionInfoGarages.getIdProvider();
                infoGaragesCollectionInfoGarages.setIdProvider(provider);
                infoGaragesCollectionInfoGarages = em.merge(infoGaragesCollectionInfoGarages);
                if (oldIdProviderOfInfoGaragesCollectionInfoGarages != null) {
                    oldIdProviderOfInfoGaragesCollectionInfoGarages.getInfoGaragesCollection().remove(infoGaragesCollectionInfoGarages);
                    oldIdProviderOfInfoGaragesCollectionInfoGarages = em.merge(oldIdProviderOfInfoGaragesCollectionInfoGarages);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Provider provider) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provider persistentProvider = em.find(Provider.class, provider.getIdProvider());
            Party idPartyOld = persistentProvider.getIdParty();
            Party idPartyNew = provider.getIdParty();
            Color idColorOld = persistentProvider.getIdColor();
            Color idColorNew = provider.getIdColor();
            Companys idCompanyOld = persistentProvider.getIdCompany();
            Companys idCompanyNew = provider.getIdCompany();
            Country idCountryOld = persistentProvider.getIdCountry();
            Country idCountryNew = provider.getIdCountry();
            Wallpapers idWallOld = persistentProvider.getIdWall();
            Wallpapers idWallNew = provider.getIdWall();
            Collection<InfoGarages> infoGaragesCollectionOld = persistentProvider.getInfoGaragesCollection();
            Collection<InfoGarages> infoGaragesCollectionNew = provider.getInfoGaragesCollection();
            if (idPartyNew != null) {
                idPartyNew = em.getReference(idPartyNew.getClass(), idPartyNew.getIdParty());
                provider.setIdParty(idPartyNew);
            }
            if (idColorNew != null) {
                idColorNew = em.getReference(idColorNew.getClass(), idColorNew.getIdColor());
                provider.setIdColor(idColorNew);
            }
            if (idCompanyNew != null) {
                idCompanyNew = em.getReference(idCompanyNew.getClass(), idCompanyNew.getIdCompany());
                provider.setIdCompany(idCompanyNew);
            }
            if (idCountryNew != null) {
                idCountryNew = em.getReference(idCountryNew.getClass(), idCountryNew.getIdCountry());
                provider.setIdCountry(idCountryNew);
            }
            if (idWallNew != null) {
                idWallNew = em.getReference(idWallNew.getClass(), idWallNew.getIdWall());
                provider.setIdWall(idWallNew);
            }
            Collection<InfoGarages> attachedInfoGaragesCollectionNew = new ArrayList<InfoGarages>();
            for (InfoGarages infoGaragesCollectionNewInfoGaragesToAttach : infoGaragesCollectionNew) {
                infoGaragesCollectionNewInfoGaragesToAttach = em.getReference(infoGaragesCollectionNewInfoGaragesToAttach.getClass(), infoGaragesCollectionNewInfoGaragesToAttach.getIdinfGar());
                attachedInfoGaragesCollectionNew.add(infoGaragesCollectionNewInfoGaragesToAttach);
            }
            infoGaragesCollectionNew = attachedInfoGaragesCollectionNew;
            provider.setInfoGaragesCollection(infoGaragesCollectionNew);
            provider = em.merge(provider);
            if (idPartyOld != null && !idPartyOld.equals(idPartyNew)) {
                idPartyOld.getProviderCollection().remove(provider);
                idPartyOld = em.merge(idPartyOld);
            }
            if (idPartyNew != null && !idPartyNew.equals(idPartyOld)) {
                idPartyNew.getProviderCollection().add(provider);
                idPartyNew = em.merge(idPartyNew);
            }
            if (idColorOld != null && !idColorOld.equals(idColorNew)) {
                idColorOld.getProviderCollection().remove(provider);
                idColorOld = em.merge(idColorOld);
            }
            if (idColorNew != null && !idColorNew.equals(idColorOld)) {
                idColorNew.getProviderCollection().add(provider);
                idColorNew = em.merge(idColorNew);
            }
            if (idCompanyOld != null && !idCompanyOld.equals(idCompanyNew)) {
                idCompanyOld.getProviderCollection().remove(provider);
                idCompanyOld = em.merge(idCompanyOld);
            }
            if (idCompanyNew != null && !idCompanyNew.equals(idCompanyOld)) {
                idCompanyNew.getProviderCollection().add(provider);
                idCompanyNew = em.merge(idCompanyNew);
            }
            if (idCountryOld != null && !idCountryOld.equals(idCountryNew)) {
                idCountryOld.getProviderCollection().remove(provider);
                idCountryOld = em.merge(idCountryOld);
            }
            if (idCountryNew != null && !idCountryNew.equals(idCountryOld)) {
                idCountryNew.getProviderCollection().add(provider);
                idCountryNew = em.merge(idCountryNew);
            }
            if (idWallOld != null && !idWallOld.equals(idWallNew)) {
                idWallOld.getProviderCollection().remove(provider);
                idWallOld = em.merge(idWallOld);
            }
            if (idWallNew != null && !idWallNew.equals(idWallOld)) {
                idWallNew.getProviderCollection().add(provider);
                idWallNew = em.merge(idWallNew);
            }
            for (InfoGarages infoGaragesCollectionOldInfoGarages : infoGaragesCollectionOld) {
                if (!infoGaragesCollectionNew.contains(infoGaragesCollectionOldInfoGarages)) {
                    infoGaragesCollectionOldInfoGarages.setIdProvider(null);
                    infoGaragesCollectionOldInfoGarages = em.merge(infoGaragesCollectionOldInfoGarages);
                }
            }
            for (InfoGarages infoGaragesCollectionNewInfoGarages : infoGaragesCollectionNew) {
                if (!infoGaragesCollectionOld.contains(infoGaragesCollectionNewInfoGarages)) {
                    Provider oldIdProviderOfInfoGaragesCollectionNewInfoGarages = infoGaragesCollectionNewInfoGarages.getIdProvider();
                    infoGaragesCollectionNewInfoGarages.setIdProvider(provider);
                    infoGaragesCollectionNewInfoGarages = em.merge(infoGaragesCollectionNewInfoGarages);
                    if (oldIdProviderOfInfoGaragesCollectionNewInfoGarages != null && !oldIdProviderOfInfoGaragesCollectionNewInfoGarages.equals(provider)) {
                        oldIdProviderOfInfoGaragesCollectionNewInfoGarages.getInfoGaragesCollection().remove(infoGaragesCollectionNewInfoGarages);
                        oldIdProviderOfInfoGaragesCollectionNewInfoGarages = em.merge(oldIdProviderOfInfoGaragesCollectionNewInfoGarages);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = provider.getIdProvider();
                if (findProvider(id) == null) {
                    throw new NonexistentEntityException("The provider with id " + id + " no longer exists.");
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
            Provider provider;
            try {
                provider = em.getReference(Provider.class, id);
                provider.getIdProvider();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provider with id " + id + " no longer exists.", enfe);
            }
            Party idParty = provider.getIdParty();
            if (idParty != null) {
                idParty.getProviderCollection().remove(provider);
                idParty = em.merge(idParty);
            }
            Color idColor = provider.getIdColor();
            if (idColor != null) {
                idColor.getProviderCollection().remove(provider);
                idColor = em.merge(idColor);
            }
            Companys idCompany = provider.getIdCompany();
            if (idCompany != null) {
                idCompany.getProviderCollection().remove(provider);
                idCompany = em.merge(idCompany);
            }
            Country idCountry = provider.getIdCountry();
            if (idCountry != null) {
                idCountry.getProviderCollection().remove(provider);
                idCountry = em.merge(idCountry);
            }
            Wallpapers idWall = provider.getIdWall();
            if (idWall != null) {
                idWall.getProviderCollection().remove(provider);
                idWall = em.merge(idWall);
            }
            Collection<InfoGarages> infoGaragesCollection = provider.getInfoGaragesCollection();
            for (InfoGarages infoGaragesCollectionInfoGarages : infoGaragesCollection) {
                infoGaragesCollectionInfoGarages.setIdProvider(null);
                infoGaragesCollectionInfoGarages = em.merge(infoGaragesCollectionInfoGarages);
            }
            em.remove(provider);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Provider> findProviderEntities() {
        return findProviderEntities(true, -1, -1);
    }

    public List<Provider> findProviderEntities(int maxResults, int firstResult) {
        return findProviderEntities(false, maxResults, firstResult);
    }

    private List<Provider> findProviderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Provider.class));
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

    public Provider findProvider(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Provider.class, id);
        } finally {
            em.close();
        }
    }

    public int getProviderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Provider> rt = cq.from(Provider.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
 
    public List<Wallpapers> findNameList() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("SELECT Name FROM wallpapers");
            return (List) q.getSingleResult();
        } finally {
            em.close();
        }
    }
}
