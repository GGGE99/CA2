/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author marcg
 */
@Entity
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "address")
    private List<Person> persons;
    private String street;
    @ManyToOne
    private CityInfo cityInfo;

    public Address() {
    }

    public Address(String street, CityInfo cityInfo) {
        this.street = street;
        this.cityInfo = cityInfo;
        this.persons = new ArrayList();
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }

    public void setCityInfo(CityInfo cityInfo) {
        if (cityInfo != null) {
            this.cityInfo = cityInfo;
            cityInfo.addAddress(this);
        } else {
            this.cityInfo = null;
        }
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" + "id=" + id + ", street=" + street + ", cityInfo=" + cityInfo + '}';
    }

    
    
}
