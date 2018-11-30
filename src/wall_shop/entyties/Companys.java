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
    @NamedQuery(name = "Companys.findAll", query = "SELECT c FROM Companys c")
    , @NamedQuery(name = "Companys.findByIdCompany", query = "SELECT c FROM Companys c WHERE c.idCompany = :idCompany")
    , @NamedQuery(name = "Companys.findByNamecompany", query = "SELECT c FROM Companys c WHERE c.namecompany = :namecompany")})
public class Companys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_company")
    private Integer idCompany;
    @Basic(optional = false)
    @Column(name = "Name_company")
    private String namecompany;
    @OneToMany(mappedBy = "idCompany")
    private Collection<Provider> providerCollection;

    public Companys() {
    }

    public Companys(Integer idCompany) {
        this.idCompany = idCompany;
    }

    public Companys(Integer idCompany, String namecompany) {
        this.idCompany = idCompany;
        this.namecompany = namecompany;
    }

    public Integer getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Integer idCompany) {
        this.idCompany = idCompany;
    }

    public String getNamecompany() {
        return namecompany;
    }

    public void setNamecompany(String namecompany) {
        this.namecompany = namecompany;
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
        hash += (idCompany != null ? idCompany.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Companys)) {
            return false;
        }
        Companys other = (Companys) object;
        if ((this.idCompany == null && other.idCompany != null) || (this.idCompany != null && !this.idCompany.equals(other.idCompany))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wall_shop.entyties.Companys[ idCompany=" + idCompany + " ]";
    }
    
        @Transient 
        public int state = 0;
}
