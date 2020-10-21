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
import facades.HobbyFacade;
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

//        Person person1 = new Person(Date.valueOf("2020-10-10"), "IDK", "LORT", "adasdadadasd@sdasdasd.cascas");
//        Phone phone = new Phone("42422211", "+45");
//        Address address = new Address("LORTegade 21",new CityInfo("8732"));
//
//        person1.addPhone(phone);
//        person1.setAddress(address);
//
//
//        PersonDTO personDTO = new PersonDTO(person1);
//        personDTO.addID(125);
//        Facade.addPerson(personDTO);
        
//        Person person2 = new Person(Date.valueOf("2020-10-10"), "Marcus", "LORT", "adasdadadasd@sdasdasd.cascas");
//        Phone phone2 = new Phone("42421111", "+45");
//        Address address2 = new Address("LORTegade 21",new CityInfo("8732"));
//
//        person2.addPhone(phone2);
//        person2.setAddress(address2);
//        PersonDTO personDTO2 = new PersonDTO(person2);
//        personDTO2.addID(5);
//        personDTO2.setId(15);
//        Facade.editPerson(personDTO2);
        
        //System.out.println(HobbyFacade.getHobbyFacade(emf).allHobbies());
        
        Facade.findPersonByPhone("11112222");
    }
}
