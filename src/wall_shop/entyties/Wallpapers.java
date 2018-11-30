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
    @NamedQuery(name = "Wallpapers.findAll", query = "SELECT w FROM Wallpapers w")
    , @NamedQuery(name = "Wallpapers.findByIdWall", query = "SELECT w FROM Wallpapers w WHERE w.idWall = :idWall")
    , @NamedQuery(name = "Wallpapers.findByName", query = "SELECT w FROM Wallpapers w WHERE w.name = :name")
    , @NamedQuery(name = "Wallpapers.findByArticles", query = "SELECT w FROM Wallpapers w WHERE w.articles = :articles")})
public class Wallpapers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_wall")
    private Integer idWall;
    private String name;
    @Basic(optional = false)
    private String articles;
    @OneToMany(mappedBy = "idWall")
    private Collection<Provider> providerCollection;

    
    public Wallpapers() {
    }
    public Wallpapers(Integer idWall) {
        this.idWall = idWall;
    }

    public Wallpapers(Integer idWall, String articles) {
        this.idWall = idWall;
        this.articles = articles;
    }

    public Integer getIdWall() {
        return idWall;
    }

    public void setIdWall(Integer idWall) {
        this.idWall = idWall;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticles() {
        return articles;
    }

    public void setArticles(String articles) {
        this.articles = articles;
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
        hash += (idWall != null ? idWall.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wallpapers)) {
            return false;
        }
        Wallpapers other = (Wallpapers) object;
        if ((this.idWall == null && other.idWall != null) || (this.idWall != null && !this.idWall.equals(other.idWall))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wall_shop.entyties.Wallpapers[ idWall=" + idWall + " ]";
    }
  
@Transient 
public int state = 0;
    
}
