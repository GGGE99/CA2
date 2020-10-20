/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dto.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import exceptions.MissingInputException;
import facades.PersonFacade;
import java.sql.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author marcg
 */
public class Test {

    private static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    private static PersonFacade Facade = PersonFacade.getFacadeExample(emf);

    public static void main(String[] args) throws MissingInputException {
        EntityManager em = emf.createEntityManager();

        Person person1 = new Person(Date.valueOf("2020-10-10"), "IDK", "LORT", "adasdadadasd@sdasdasd.cascas");
        Phone phone = new Phone("44444443", "+45");
        Address address = new Address("LORTegade 21");
        CityInfo cityInfo = new CityInfo("8732");
        Hobby hobby = Facade.findHobbyById(121);
            
        person1.addHobby(hobby);
        person1.addPhone(phone);
        address.setCityInfo(cityInfo);
        person1.setAddress(address);

//        System.out.println(person);
        PersonDTO personDTO = new PersonDTO(person1);
        
//        System.out.println(personDTO);

//        PersonDTO dto = new PersonDTO(person);
        Facade.addPerson(personDTO);
    }
}
