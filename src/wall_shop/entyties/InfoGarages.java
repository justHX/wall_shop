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
@Table(name = "info_garages", catalog = "wall_shop", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InfoGarages.findAll", query = "SELECT i FROM InfoGarages i")
    , @NamedQuery(name = "InfoGarages.findByIdinfGar", query = "SELECT i FROM InfoGarages i WHERE i.idinfGar = :idinfGar")
    , @NamedQuery(name = "InfoGarages.findByNumberroll", query = "SELECT i FROM InfoGarages i WHERE i.numberroll = :numberroll")
    , @NamedQuery(name = "InfoGarages.findByLength", query = "SELECT i FROM InfoGarages i WHERE i.length = :length")
    , @NamedQuery(name = "InfoGarages.findByWidth", query = "SELECT i FROM InfoGarages i WHERE i.width = :width")})
public class InfoGarages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_infGar")
    private Integer idinfGar;
    @Basic(optional = false)
    @Column(name = "Number_roll")
    private Integer numberroll;
    @Basic(optional = false)
    private Float length;
    @Basic(optional = false)
    private Float width;
    @OneToMany(mappedBy = "idinfGar")
    private Collection<Reservet> reservetCollection;
    @JoinColumn(name = "id_provider", referencedColumnName = "id_provider")
    @ManyToOne
    private Provider idProvider;
    @JoinColumn(name = "id_garage", referencedColumnName = "id_garage")
    @ManyToOne
    private Garages idGarage;
    @JoinColumn(name = "id_wall", referencedColumnName = "id_wall")
    @ManyToOne
    private Wallpapers idWall;
    @JoinColumn(name = "id_company", referencedColumnName = "id_company")
    @ManyToOne
    private Companys idCompany;
    
    public InfoGarages() {
    }

    public InfoGarages(Integer idinfGar) {
        this.idinfGar = idinfGar;
    }

    public InfoGarages(Integer idinfGar, Integer numberroll, Float length, Float width) {
        this.idinfGar = idinfGar;
        this.numberroll = numberroll;
        this.length = length;
        this.width = width;
    }

    public Integer getIdinfGar() {
        return idinfGar;
    }

    public void setIdinfGar(Integer idinfGar) {
        this.idinfGar = idinfGar;
    }

    public Integer getNumberroll() {
        return numberroll;
    }

    public void setNumberroll(Integer numberroll) {
        this.numberroll = numberroll;
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

    @XmlTransient
    public Collection<Reservet> getReservetCollection() {
        return reservetCollection;
    }

    public void setReservetCollection(Collection<Reservet> reservetCollection) {
        this.reservetCollection = reservetCollection;
    }

    public Provider getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Provider idProvider) {
        this.idProvider = idProvider;
    }

    public Garages getIdGarage() {
        return idGarage;
    }

    public void setIdGarage(Garages idGarage) {
        this.idGarage = idGarage;
    }
    
    public Wallpapers getIdWall(){
    return idWall;
    }
    
    public void setIdWall(Wallpapers idWall){
    this.idWall = idWall;
    }
    
    public Companys getIdCompany(){
    return idCompany;
    }

    public void setIdCompany(Companys idCompany){
    this.idCompany = idCompany;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinfGar != null ? idinfGar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoGarages)) {
            return false;
        }
        InfoGarages other = (InfoGarages) object;
        if ((this.idinfGar == null && other.idinfGar != null) || (this.idinfGar != null && !this.idinfGar.equals(other.idinfGar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wall_shop.entyties.InfoGarages[ idinfGar=" + idinfGar + " ]";
    }
    
        
    @Transient 
    public int state = 0;
    
}
