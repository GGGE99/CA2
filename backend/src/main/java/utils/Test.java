///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package utils;
//
//import dto.PersonDTO;
//import entities.Address;
//import entities.CityInfo;
//import entities.Hobby;
//import entities.Person;
//import entities.Phone;
//import exceptions.MissingInputException;
//import exceptions.PersonNotFoundException;
//import facades.HobbyFacade;
//import facades.PersonFacade;
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//
///**
// *
// * @author marcg
// */
//public class Test {
//
//    private static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//    private static PersonFacade Facade = PersonFacade.getFacadeExample(emf);
//
//    public static void main(String[] args) throws MissingInputException, PersonNotFoundException {
//        EntityManager em = emf.createEntityManager();
//
//        Person person1 = new Person(Date.valueOf("2020-10-10"), "HEJ", "LORT", "adasdadadasd@sdasdasd.cascas");
//        Phone phone = new Phone("12345699", "+45");
//        Address address = new Address("LORTegade 21",new CityInfo("8732"));
//
//        person1.addPhone(phone);
//        person1.setAddress(address);
//        person1.addHobby(em.find(Hobby.class, 1));
//
//        PersonDTO personDTO = new PersonDTO(person1);
////        personDTO.addID(14);
//        Facade.addPerson(personDTO);
////        Person person2 = new Person(Date.valueOf("2020-10-10"), "Marcus", "LORT", "marcus@sdasdasd.cascas");
//////        Phone phone2 = );
////        Address address2 = new Address("SkodVej 69",new CityInfo("8732"));
////
////        person2.addPhone(new Phone("42424242", "+90"));
////        person2.setAddress(address2);
////        PersonDTO personDTO2 = new PersonDTO(person2);
////        personDTO2.addID(12);
////        personDTO2.setId(7);
////        Facade.editPerson(personDTO2);
////        Person person2 = new Person(7, Date.valueOf("2020-10-10"), "Marcus", "LORT", "adasdadadasd@sdasdasd.cascas");
////        Phone phone2 = new Phone("42421111", "+45");
////        Phone phone3 = new Phone("11112222", "+45");
////
////        Address address2 = new Address("Skodvej 21", new CityInfo("8732"));
//////
////        person2.addPhone(phone2);
////        person2.addPhone(phone3);
////
////        person2.setAddress(address2);
////        PersonDTO personDTO2 = new PersonDTO(person2);
////        Facade.editPerson(personDTO2);
////
////        List<Integer> hobbylist = new ArrayList();
////        hobbylist.add(3);
////        hobbylist.add(2);
////        hobbylist.add(1);
////
//////        Facade.editPersonHobby(7,hobbylist);
////        List<Phone> phoneList = new ArrayList();
////        phoneList.add(new Phone("12345678", "+45"));
////        phoneList.add(new Phone("11112222", "+45"));
//
////        Facade.editPersonPhone(7, phoneList);
////        Facade.editPersonAddPhone(7, phoneList);
//        //System.out.println(HobbyFacade.getHobbyFacade(emf).allHobbies());
//        //System.out.println(Facade.findPersonByPhone("11112222"));
//    }
//}
