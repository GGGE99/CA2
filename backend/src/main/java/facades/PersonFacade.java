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
            em.getTransaction().begin();
            em.persist(person2);
            em.getTransaction().commit();

            em.getTransaction().begin();
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

            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person2);
    }

    public PersonDTO editPerson(PersonDTO personDTO) throws MissingInputException {
        EntityManager em = getEntityManager();

        Person person;
        person = em.find(Person.class, personDTO.getId());
        ArrayList<Hobby> hobbies = new ArrayList();

        for (int i : personDTO.getHobbies()) {
            hobbies.add(em.find(Hobby.class, i));
        }

        person.setId(personDTO.getId());
        person.setName(personDTO.getName());
        person.setBirthday(personDTO.getBirthday());
        person.setEmail(personDTO.getEmail());
        person.setGender(personDTO.getGender());
        person.setPhones(personDTO.getPhones());
        CityInfo cityInfo = new CityInfo(personDTO.getZipCode());
        Address address = new Address(personDTO.getStreet(), cityInfo);
        person.setAddress(address);
        person.setHobbies(hobbies);
        
        //todo se om adresse er der i forvejen og finde ud af hvordan fuck vi opdater det her 

        try {
//            em.getTransaction().begin();
//
////            Query query = em.createQuery("SELECT c FROM CityInfo c WHERE c.zipCode = :zipcode");
////            query.setParameter("zipcode", personDTO.getZipCode());
////            CityInfo cityInfo = (CityInfo) query.getSingleResult();
////            person.setAddress(new Address(personDTO.getStreet(), cityInfo));
////            for (int i : personDTO.getHobbies()) {
////                person.addHobby(em.find(Hobby.class, i));
////            }
//            em.getTransaction().commit();
        } finally {
            em.close();
        }
        System.out.println(person);

        System.out.println(new PersonDTO(person));
        return new PersonDTO(person);
    }

    public PersonDTO findPersonByPhone(String phoneNr) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT p FROM Person p WHERE phone.number = :phoneNr");
        query.setParameter("phoneNr", phoneNr);
        Person person = (Person) query.getSingleResult();
        return new PersonDTO(person);

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
