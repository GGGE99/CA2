/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.List;
import entities.*;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author madsa
 */
public class PersonDTO {

    private Integer id;
    private String name;
    private String gender;
    private String email;
    private Date birthday;
    private String street;
    private String zipCode;
    private String city;
    private List<Integer> hobbiesID;
    private List<Phone> phones;


    public PersonDTO() {
    }

    public PersonDTO(Person p) {
        this.id = p.getId();    
        this.name = p.getName();
        this.gender = p.getGender();
        this.email = p.getEmail();
        this.birthday = p.getBirthday();
        this.street = p.getAddress().getStreet();
        this.zipCode = p.getAddress().getCityInfo().getZipCode();
        this.city = p.getAddress().getCityInfo().getCity();
        this.phones = p.getPhones();
        hobbiesID = new ArrayList();
        for (Hobby h : p.getHobbies()) {
            this.hobbiesID.add(h.getId());
        }
    }
    
    public void addID(int id){
        hobbiesID.add(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public List<Integer> getHobbies() {
        return hobbiesID;
    }

    public void setHobbies(List<Integer> hobbies) {
        this.hobbiesID = hobbies;
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "id=" + id + ", name=" + name + ", gender=" + gender + ", email=" + email + ", birthday=" + birthday + ", street=" + street + ", zipCode=" + zipCode + ", city=" + city + ", hobbies=" + hobbiesID + ", phones=" + phones + '}';
    }
    
    

}
