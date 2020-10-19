/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author marcg
 */
@Entity
public class CityInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 4)
    private String zipCode;
    @Column(length = 35)
    private String city;
    @OneToMany(mappedBy = "cityInfo", cascade = CascadeType.PERSIST)
    private List<Address> address;
    
    public CityInfo() {
    }

    public CityInfo(String zipCode, String city, List<Address> address) {
        this.zipCode = zipCode;
        this.city = city;
        this.address = address;
    }
    
    

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CityInfo(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }
}
