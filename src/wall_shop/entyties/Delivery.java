/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall_shop.entyties;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hulk-
 */
@Entity
@Table(catalog = "wall_shop", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Delivery.findAll", query = "SELECT d FROM Delivery d")
    , @NamedQuery(name = "Delivery.findByIdDelivery", query = "SELECT d FROM Delivery d WHERE d.idDelivery = :idDelivery")
    , @NamedQuery(name = "Delivery.findByCity", query = "SELECT d FROM Delivery d WHERE d.city = :city")
    , @NamedQuery(name = "Delivery.findByStreet", query = "SELECT d FROM Delivery d WHERE d.street = :street")
    , @NamedQuery(name = "Delivery.findByHousenum", query = "SELECT d FROM Delivery d WHERE d.housenum = :housenum")
    , @NamedQuery(name = "Delivery.findByKorpus", query = "SELECT d FROM Delivery d WHERE d.korpus = :korpus")
    , @NamedQuery(name = "Delivery.findByKvartira", query = "SELECT d FROM Delivery d WHERE d.kvartira = :kvartira")
    , @NamedQuery(name = "Delivery.findByNumdeliv", query = "SELECT d FROM Delivery d WHERE d.numdeliv = :numdeliv")})
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_delivery")
    private Integer idDelivery;
    @Basic(optional = false)
    private String city;
    @Basic(optional = false)
    private String street;
    @Basic(optional = false)
    @Column(name = "House_num")
    private Integer housenum;
    @Basic(optional = false)
    private String korpus;
    @Basic(optional = false)
    private Integer kvartira;
    @Basic(optional = false)
    @Column(name = "Num_deliv")
    private Integer numdeliv;
    @JoinColumn(name = "id_reserv", referencedColumnName = "id_reserv")
    @ManyToOne
    private Reservet idReserv;
   

    public Delivery() {
    }

    public Delivery(Integer idDelivery) {
        this.idDelivery = idDelivery;
    }

    public Delivery(Integer idDelivery, String city, String street, Integer housenum, String korpus, Integer kvartira, Integer numdeliv) {
        this.idDelivery = idDelivery;
        this.city = city;
        this.street = street;
        this.housenum = housenum;
        this.korpus = korpus;
        this.kvartira = kvartira;
        this.numdeliv = numdeliv;
    }

    
    
    public Integer getIdDelivery() {
        return idDelivery;
    }

    public void setIdDelivery(Integer idDelivery) {
        this.idDelivery = idDelivery;
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

    public String getKorpus() {
        return korpus;
    }

    public void setKorpus(String korpus) {
        this.korpus = korpus;
    }

    public Integer getKvartira() {
        return kvartira;
    }

    public void setKvartira(Integer kvartira) {
        this.kvartira = kvartira;
    }

    public Integer getNumdeliv() {
        return numdeliv;
    }

    public void setNumdeliv(Integer numdeliv) {
        this.numdeliv = numdeliv;
    }

    public Reservet getIdReserv() {
        return idReserv;
    }

    public void setIdReserv(Reservet idReserv) {
        this.idReserv = idReserv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDelivery != null ? idDelivery.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Delivery)) {
            return false;
        }
        Delivery other = (Delivery) object;
        if ((this.idDelivery == null && other.idDelivery != null) || (this.idDelivery != null && !this.idDelivery.equals(other.idDelivery))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wall_shop.entyties.Delivery[ idDelivery=" + idDelivery + " ]";
    }
    
     @Transient 
    public int state = 0;
}
