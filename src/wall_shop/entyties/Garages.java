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
    @NamedQuery(name = "Garages.findAll", query = "SELECT g FROM Garages g")
    , @NamedQuery(name = "Garages.findByIdGarage", query = "SELECT g FROM Garages g WHERE g.idGarage = :idGarage")
    , @NamedQuery(name = "Garages.findByName", query = "SELECT g FROM Garages g WHERE g.name = :name")
    , @NamedQuery(name = "Garages.findByCity", query = "SELECT g FROM Garages g WHERE g.city = :city")
    , @NamedQuery(name = "Garages.findByStreet", query = "SELECT g FROM Garages g WHERE g.street = :street")
    , @NamedQuery(name = "Garages.findByHousenum", query = "SELECT g FROM Garages g WHERE g.housenum = :housenum")})
public class Garages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_garage")
    private Integer idGarage;
    private String name;
    @Basic(optional = false)
    private String city;
    @Basic(optional = false)
    private String street;
    @Basic(optional = false)
    @Column(name = "House_num")
    private Integer housenum;
    @OneToMany(mappedBy = "idGarage")
    private Collection<InfoGarages> infoGaragesCollection;

    public Garages() {
    }

    public Garages(Integer idGarage) {
        this.idGarage = idGarage;
    }

    public Garages(Integer idGarage, String city, String street, Integer housenum) {
        this.idGarage = idGarage;
        this.city = city;
        this.street = street;
        this.housenum = housenum;
    }

    public Integer getIdGarage() {
        return idGarage;
    }

    public void setIdGarage(Integer idGarage) {
        this.idGarage = idGarage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHousenum() {
        return housenum;
    }

    public void setHousenum(Integer housenum) {
        this.housenum = housenum;
    }

    @XmlTransient
    public Collection<InfoGarages> getInfoGaragesCollection() {
        return infoGaragesCollection;
    }

    public void setInfoGaragesCollection(Collection<InfoGarages> infoGaragesCollection) {
        this.infoGaragesCollection = infoGaragesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGarage != null ? idGarage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Garages)) {
            return false;
        }
        Garages other = (Garages) object;
        if ((this.idGarage == null && other.idGarage != null) || (this.idGarage != null && !this.idGarage.equals(other.idGarage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wall_shop.entyties.Garages[ idGarage=" + idGarage + " ]";
    }
    
    
    @Transient
    public int state = 0;
}
