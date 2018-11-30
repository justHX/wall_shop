/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall_shop.entyties;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
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
    @NamedQuery(name = "Reservet.findAll", query = "SELECT r FROM Reservet r")
    , @NamedQuery(name = "Reservet.findByIdReserv", query = "SELECT r FROM Reservet r WHERE r.idReserv = :idReserv")
    , @NamedQuery(name = "Reservet.findByWallnum", query = "SELECT r FROM Reservet r WHERE r.wallnum = :wallnum")
    , @NamedQuery(name = "Reservet.findByLength", query = "SELECT r FROM Reservet r WHERE r.length = :length")
    , @NamedQuery(name = "Reservet.findByWidth", query = "SELECT r FROM Reservet r WHERE r.width = :width")
    , @NamedQuery(name = "Reservet.findByYoNdeliv", query = "SELECT r FROM Reservet r WHERE r.yoNdeliv = :yoNdeliv")})
public class Reservet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reserv")
    private Integer idReserv;
    @Basic(optional = false)
    @Column(name = "Wall_num")
    private Integer wallnum;
    @Basic(optional = false)
    private Float length;
    @Basic(optional = false)
    private Float width;
    @Basic(optional = false)
    @Column(name = "YoN_deliv")
    private String yoNdeliv;
    @OneToMany(mappedBy = "idReserv")
    private Collection<Delivery> deliveryCollection;
    @JoinColumn(name = "id_infGar", referencedColumnName = "id_infGar")
    @ManyToOne
    private InfoGarages idinfGar;
    @JoinColumn(name = "id_wall", referencedColumnName = "id_wall")
    @ManyToOne
    private Wallpapers idWall;
    @JoinColumn(name = "id_client", referencedColumnName = "id_client")
    @ManyToOne
    private Clients idClient;
    @JoinColumn(name = "id_garage", referencedColumnName = "id_garage")
    @ManyToOne
    private Garages idGar;
    @OneToMany(mappedBy = "idReserv")
    private Collection<Selling> sellingCollection;

    public Reservet() {
    }

    public Reservet(Integer idReserv) {
        this.idReserv = idReserv;
    }

    public Reservet(Integer idReserv, Integer wallnum, Float length, Float width, String yoNdeliv) {
        this.idReserv = idReserv;
        this.wallnum = wallnum;
        this.length = length;
        this.width = width;
        this.yoNdeliv = yoNdeliv;
    }
    
    public Garages getIdGar(){
    return idGar;
    }
    
    public void setIdGar(Garages idGar){
    this.idGar = idGar;
    }

    public Wallpapers getWall(){
    return idWall;
    }
    
    public void setWall(Wallpapers wall){
    this.idWall = wall;
    }
    
    public Integer getIdReserv() {
        return idReserv;
    }

    public void setIdReserv(Integer idReserv) {
        this.idReserv = idReserv;
    }

    public Integer getWallnum() {
        return wallnum;
    }

    public void setWallnum(Integer wallnum) {
        this.wallnum = wallnum;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public String getYoNdeliv() {
        return yoNdeliv;
    }

    public void setYoNdeliv(String yoNdeliv) {
        this.yoNdeliv = yoNdeliv;
    }

    @XmlTransient
    public Collection<Delivery> getDeliveryCollection() {
        return deliveryCollection;
    }

    public void setDeliveryCollection(Collection<Delivery> deliveryCollection) {
        this.deliveryCollection = deliveryCollection;
    }

    public InfoGarages getIdinfGar() {
        return idinfGar;
    }

    public void setIdinfGar(InfoGarages idinfGar) {
        this.idinfGar = idinfGar;
    }

    public Clients getIdClient() {
        return idClient;
    }

    public void setIdClient(Clients idClient) {
        this.idClient = idClient;
    }

    @XmlTransient
    public Collection<Selling> getSellingCollection() {
        return sellingCollection;
    }

    public void setSellingCollection(Collection<Selling> sellingCollection) {
        this.sellingCollection = sellingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReserv != null ? idReserv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservet)) {
            return false;
        }
        Reservet other = (Reservet) object;
        if ((this.idReserv == null && other.idReserv != null) || (this.idReserv != null && !this.idReserv.equals(other.idReserv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wall_shop.entyties.Reservet[ idReserv=" + idReserv + " ]";
    }
 
     @Transient 
    public int state = 0;
}
