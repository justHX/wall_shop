/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall_shop.entyties;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Selling.findAll", query = "SELECT s FROM Selling s")
    , @NamedQuery(name = "Selling.findByIdSell", query = "SELECT s FROM Selling s WHERE s.idSell = :idSell")
    , @NamedQuery(name = "Selling.findByDate", query = "SELECT s FROM Selling s WHERE s.date = :date")
//    , @NamedQuery(name = "Selling.findByNumberroll", query = "SELECT s FROM Selling s WHERE s.numberroll = :numberroll")
//    , @NamedQuery(name = "Selling.findByLength", query = "SELECT s FROM Selling s WHERE s.length = :length")
//    , @NamedQuery(name = "Selling.findByWidth", query = "SELECT s FROM Selling s WHERE s.width = :width")
    , @NamedQuery(name = "Selling.findByMoney", query = "SELECT s FROM Selling s WHERE s.money = :money")})
public class Selling implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sell")
    private Integer idSell;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date date;
//    @Basic(optional = false)
//    @Column(name = "Number_roll")
//    private long numberroll;
//    @Basic(optional = false)
//    private BigInteger length;
//    @Basic(optional = false)
//    private BigInteger width;
    @Basic(optional = false)
    private Float money;
    @JoinColumn(name = "id_reserv", referencedColumnName = "id_reserv")
    @ManyToOne
    private Reservet idReserv;

    public Selling() {
    }

    public Selling(Integer idSell) {
        this.idSell = idSell;
    }

    public Selling(Integer idSell, Date date, Float money) {
        this.idSell = idSell;
        this.date = date;
//        this.numberroll = numberroll;
//        this.length = length;
//        this.width = width;
        this.money = money;
    }

    public Integer getIdSell() {
        return idSell;
    }

    public void setIdSell(Integer idSell) {
        this.idSell = idSell;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
//
//    public long getNumberroll() {
//        return numberroll;
//    }
//
//    public void setNumberroll(long numberroll) {
//        this.numberroll = numberroll;
//    }
//
//    public BigInteger getLength() {
//        return length;
//    }
//
//    public void setLength(BigInteger length) {
//        this.length = length;
//    }
//
//    public BigInteger getWidth() {
//        return width;
//    }
//
//    public void setWidth(BigInteger width) {
//        this.width = width;
//    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
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
        hash += (idSell != null ? idSell.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Selling)) {
            return false;
        }
        Selling other = (Selling) object;
        if ((this.idSell == null && other.idSell != null) || (this.idSell != null && !this.idSell.equals(other.idSell))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wall_shop.entyties.Selling[ idSell=" + idSell + " ]";
    }
    
    @Transient 
    public int state = 0;
    
}
