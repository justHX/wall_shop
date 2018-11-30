/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall_shop.entyties;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hulk-
 */
@Entity
@Table(catalog = "wall_shop", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Party.findAll", query = "SELECT p FROM Party p")
    , @NamedQuery(name = "Party.findByIdParty", query = "SELECT p FROM Party p WHERE p.idParty = :idParty")
    , @NamedQuery(name = "Party.findByPartynum", query = "SELECT p FROM Party p WHERE p.partynum = :partynum")})
public class Party implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_party")
    private Integer idParty;
    @Basic(optional = false)
    @Column(name = "Party_num")
    private String partynum;
    @OneToMany(mappedBy = "idParty")
    private Collection<Provider> providerCollection;

    public Party() {
    }

    public Party(Integer idParty) {
        this.idParty = idParty;
    }

    public Party(Integer idParty, String partynum) {
        this.idParty = idParty;
        this.partynum = partynum;
    }

    public Integer getIdParty() {
        return idParty;
    }

    public void setIdParty(Integer idParty) {
        this.idParty = idParty;
    }

    public String getPartynum() {
        return partynum;
    }

    public void setPartynum(String partynum) {
        this.partynum = partynum;
    }

    @XmlTransient
    public Collection<Provider> getProviderCollection() {
        return providerCollection;
    }

    public void setProviderCollection(Collection<Provider> providerCollection) {
        this.providerCollection = providerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParty != null ? idParty.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Party)) {
            return false;
        }
        Party other = (Party) object;
        if ((this.idParty == null && other.idParty != null) || (this.idParty != null && !this.idParty.equals(other.idParty))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wall_shop.entyties.Party[ idParty=" + idParty + " ]";
    }
 
    @Transient 
    public int state = 0;
}
