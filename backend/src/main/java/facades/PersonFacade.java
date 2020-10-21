/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author baske
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getPersonCount() {
        EntityManager em = getEntityManager();
        try {
            long personCount = (long) em.createQuery("SELECT COUNT(r) FROM Person r").getSingleResult();
            return personCount;
        } finally {
            em.close();
        }
    }

    public PersonDTO addPerson(PersonDTO personDTO) throws MissingInputException {
        EntityManager em = getEntityManager();
        Person person2 = new Person(personDTO);
        try {
            Query query = em.createQuery("SELECT c FROM CityInfo c WHERE c.zipCode = :zipcode");
            query.setParameter("zipcode", personDTO.getZipCode());
            CityInfo cityInfo = (CityInfo) query.getSingleResult();
            person2.setAddress(new Address(personDTO.getStreet(), cityInfo));
            for (int i : personDTO.getHobbies()) {
                person2.addHobby(em.find(Hobby.class, i));
            }
            for (Phone p : personDTO.getPhones()) {
                person2.addPhone(p);
            }

            em.getTransaction().begin();
            em.persist(person2);
            em.getTransaction().commit();
//
//            em.getTransaction().begin();
//
//            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person2);
    }


//    public PersonDTO editPerson(PersonDTO personDTO) throws MissingInputException {
//        EntityManager em = getEntityManager();
//
//        ArrayList<Hobby> hobbies = new ArrayList();
//
//        //todo se om adresse er der i forvejen og finde ud af hvordan fuck vi opdater det her 
//        try {
//            Person person = em.find(Person.class, personDTO.getId());
//
//            for (int i : personDTO.getHobbies()) {
//                hobbies.add(em.find(Hobby.class, i));
//            }
//            em.getTransaction().begin();
//            em.createQuery("DELETE FROM Phone WHERE Person_id = 7").executeUpdate();
////            query.setParameter("id", personDTO.getId()).executeUpdate();
//            System.out.println(personDTO.getId());
//            em.getTransaction().commit();
//
//            System.out.println(person);

//    public PersonDTO editPerson(PersonDTO personDTO) throws MissingInputException {
//        EntityManager em = getEntityManager();
//
//        Person person;
//        person = em.find(Person.class, personDTO.getId());
//        ArrayList<Hobby> hobbies = new ArrayList();
//
//        for (int i : personDTO.getHobbies()) {
//            hobbies.add(em.find(Hobby.class, i));
//        }
//
//        person.setId(personDTO.getId());
//        person.setName(personDTO.getName());
//        person.setBirthday(personDTO.getBirthday());
//        person.setEmail(personDTO.getEmail());
//        person.setGender(personDTO.getGender());
//        person.setPhones(personDTO.getPhones());
//        CityInfo cityInfo = new CityInfo(personDTO.getZipCode());
//        Address address = new Address(personDTO.getStreet(), cityInfo);
//        person.setAddress(address);
//        person.setHobbies(hobbies);
//
//        //todo se om adresse er der i forvejen og finde ud af hvordan fuck vi opdater det her 
//        try {
//
//            em.getTransaction().begin();
//
//            person.setName(personDTO.getName());
//            person.setBirthday(personDTO.getBirthday());
//            person.setEmail(personDTO.getEmail());
//            person.setGender(personDTO.getGender());
////            person.getPhones().personDTO.getPhones());
//
////            CityInfo cityInfo = (CityInfo) query.getSingleResult();
////            CityInfo cityInfo = new CityInfo(personDTO.getZipCode());
////            Address address = new Address(personDTO.getStreet(), cityInfo);
////            person.setAddress(address);
////            for (Hobby hobby : person.getHobbies()) {
////                hobby.s
////            }
//            person.setHobbies(hobbies);
////            System.out.println(person);
//            em.getTransaction().commit();
//            return new PersonDTO(person);
//        } finally {
//            em.close();
//        }
//    }

    public List<String> findPersonByPhone(String number) {
        EntityManager em = getEntityManager();
        List<String> personAndShit = new ArrayList<>();

        Query query = em.createQuery("Select pers.name, pers.birthday, pers.email, pho.number, adr.street, cInf.city, cInf.zipCode, \n"
                + "hob.name, hob.category, hob.type, hob.wikiLink \n"
                + "from Person pers\n"
                + "join pers.phones pho\n"
                + "join pers.hobbies hob\n"
                + "join pers.address adr\n"
                + "join adr.cityInfo cInf\n"
                + "where pho.number = :number");
        query.setParameter("number", number);
        personAndShit = query.getResultList();
        return personAndShit;

    }

    public PersonDTO deletePerson(long id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);
        if (person == null) {
            throw new PersonNotFoundException(String.format("Person with id: (%d) not found", id));
        } else {
            try {
                em.getTransaction().begin();
                em.remove(person);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new PersonDTO(person);
        }
    }

    public PersonDTO getPerson(long id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();

        try {
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException(String.format("Person with id: (%d) not found.", id));
            } else {
                return new PersonDTO(person);
            }
        } finally {
            em.close();
        }
    }

    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            return new PersonsDTO(em.createNamedQuery("Person.getAllRows").getResultList());
        } finally {
            em.close();
        }
    }

    /*
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException, MissingInputException {
        if ((p.getfName().length() == 0) || (p.getlName().length() == 0)) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        }
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();

            Person person = em.find(Person.class, p.getId());
            if (person == null) {
                throw new PersonNotFoundException(String.format("Person with id: (%d) not found", p.getId()));
            } else {
                person.setFirstName(p.getfName());
                person.setLastName(p.getlName());
                person.setPhone(p.getPhone());
                person.setLastEdited();
                person.getAddress().setStreet(p.getStreet());
                person.getAddress().setZip(p.getZip());
                person.getAddress().setCity(p.getCity());
            }
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }

    }
     */
}
