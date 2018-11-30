/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall_shop.entyties;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Provider.findAll", query = "SELECT p FROM Provider p")
    , @NamedQuery(name = "Provider.findByIdProvider", query = "SELECT p FROM Provider p WHERE p.idProvider = :idProvider")
    , @NamedQuery(name = "Provider.findByDatedelivery", query = "SELECT p FROM Provider p WHERE p.datedelivery = :datedelivery")
    , @NamedQuery(name = "Provider.findByNumberroll", query = "SELECT p FROM Provider p WHERE p.numberroll = :numberroll")
    , @NamedQuery(name = "Provider.findByMoney", query = "SELECT p FROM Provider p WHERE p.money = :money")})
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_provider")
    private Integer idProvider;
    @Basic(optional = false)
    @Column(name = "Date_delivery")
    @Temporal(TemporalType.DATE)
    private Date datedelivery;
    @Basic(optional = false)
    @Column(name = "Number_roll")
    private int numberroll;
    @Basic(optional = false)
    private float money;
    @JoinColumn(name = "id_party", referencedColumnName = "id_party")
    @ManyToOne
    private Party idParty;
    @JoinColumn(name = "id_color", referencedColumnName = "id_color")
    @ManyToOne
    private Color idColor;
    @JoinColumn(name = "id_company", referencedColumnName = "id_company")
    @ManyToOne
    private Companys idCompany;
    @JoinColumn(name = "id_country", referencedColumnName = "id_country")
    @ManyToOne
    private Country idCountry;
    @JoinColumn(name = "id_wall", referencedColumnName = "id_wall")
    @ManyToOne
    private Wallpapers idWall;
    @OneToMany(mappedBy = "idProvider")
    private Collection<InfoGarages> infoGaragesCollection;

    public Provider() {
    }

    public Provider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public Provider(Integer idProvider, Date datedelivery, int numberroll, float money) {
        this.idProvider = idProvider;
        this.datedelivery = datedelivery;
        this.numberroll = numberroll;
        this.money = money;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public Date getDatedelivery() {
        return datedelivery;
    }

    public void setDatedelivery(Date datedelivery) {
        this.datedelivery = datedelivery;
    }

    public int getNumberroll() {
        return numberroll;
    }

    public void setNumberroll(int numberroll) {
        this.numberroll = numberroll;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Party getIdParty() {
        return idParty;
    }

    public void setIdParty(Party idParty) {
        this.idParty = idParty;
    }

    public Color getIdColor() {
        return idColor;
    }

    public void setIdColor(Color idColor) {
        this.idColor = idColor;
    }

    public Companys getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Companys idCompany) {
        this.idCompany = idCompany;
    }

    public Country getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Country idCountry) {
        this.idCountry = idCountry;
    }

    public Wallpapers getIdWall() {
        return idWall;
    }

    public void setIdWall(Wallpapers idWall) {
        this.idWall = idWall;
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
        hash += (idProvider != null ? idProvider.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provider)) {
            return false;
        }
        Provider other = (Provider) object;
        if ((this.idProvider == null && other.idProvider != null) || (this.idProvider != null && !this.idProvider.equals(other.idProvider))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wall_shop.entyties.Provider[ idProvider=" + idProvider + " ]";
    }
 
    @Transient
    public int state = 0;
}
