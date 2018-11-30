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
    @NamedQuery(name = "Clients.findAll", query = "SELECT c FROM Clients c")
    , @NamedQuery(name = "Clients.findByIdClient", query = "SELECT c FROM Clients c WHERE c.idClient = :idClient")
    , @NamedQuery(name = "Clients.findByName", query = "SELECT c FROM Clients c WHERE c.name = :name")
    , @NamedQuery(name = "Clients.findByPhonenum", query = "SELECT c FROM Clients c WHERE c.phonenum = :phonenum")})
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_client")
    private Integer idClient;
    @Basic(optional = false)
    private String name;
    @Basic(optional = false)
    @Column(name = "Phone_num")
    private String phonenum;
    @OneToMany(mappedBy = "idClient")
    private Collection<Reservet> reservetCollection;

    public Clients() {
    }

    public Clients(Integer idClient) {
        this.idClient = idClient;
    }

    public Clients(Integer idClient, String name, String phonenum) {
        this.idClient = idClient;
        this.name = name;
        this.phonenum = phonenum;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    @XmlTransient
    public Collection<Reservet> getReservetCollection() {
        return reservetCollection;
    }

    public void setReservetCollection(Collection<Reservet> reservetCollection) {
        this.reservetCollection = reservetCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClient != null ? idClient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clients)) {
            return false;
        }
        Clients other = (Clients) object;
        if ((this.idClient == null && other.idClient != null) || (this.idClient != null && !this.idClient.equals(other.idClient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wall_shop.entyties.Clients[ idClient=" + idClient + " ]";
    }
    
     @Transient 
    public int state = 0;
}
