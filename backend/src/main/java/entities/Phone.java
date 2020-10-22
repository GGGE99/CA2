/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author marcg
 */
@Entity
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 8)
    private String number;
    @Column(length = 3)
    private String description;
    @ManyToOne
    private Person person;

    public Phone() {
    }

    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }
    
    public void addPerson(Person person) {
        this.person = person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
    
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    
}
