/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.HobbyDTO;
import dto.PersonDTO;
import dto.PersonsDTO;
import dto.PhoneDTO;
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
import javax.persistence.TypedQuery;

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
            for (int i : personDTO.getHobbiesID()) {
                person2.addHobby(em.find(Hobby.class, i));
            }
            for (PhoneDTO p : personDTO.getPhones()) {
                person2.addPhone(new Phone(p.getNumber(), p.getDescription()));
            }

            em.getTransaction().begin();
            em.persist(person2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person2);
    }

//    public Hobby getHobby(int personID) {
//        EntityManager em = getEntityManager();
//        Hobby p = em.find(Hobby.class, personID);
//        return p;
//    }
    public Person editPersonHobby(int personID, List<HobbyDTO> hobbies) throws MissingInputException {
        EntityManager em = getEntityManager();
        Person p = em.find(Person.class, personID);
        em.getTransaction().begin();
        for (Hobby hobby : p.getHobbies()) {
            hobby.removePerson(p);
        }
        for (HobbyDTO i : hobbies) {
            p.addHobby(em.find(Hobby.class, i.getId()));
        }
        em.getTransaction().commit();
        return p;
    }

    public Person editPersonPhone(int personID, List<PhoneDTO> phones) {
        EntityManager em = getEntityManager();
        Person p = em.find(Person.class, personID);
        try {
            em.getTransaction().begin();
            for (Phone phone : p.getPhones()) {
                em.remove(em.find(Phone.class, phone.getNumber()));
            }
            em.getTransaction().commit();
        } finally {
            editPersonAddPhone(personID, phones);
        }

        return p;
    }

    public Person editPersonAddPhone(int personID, List<PhoneDTO> phones) {
        EntityManager em = getEntityManager();
        Person p = em.find(Person.class, personID);
        em.getTransaction().begin();
        for (PhoneDTO phone : phones) {
            p.addPhone(new Phone(phone.getNumber(), phone.getDescription()));
        }
        em.persist(p);
        em.getTransaction().commit();
        return p;
    }

    public PersonDTO editPerson(PersonDTO personDTO) throws MissingInputException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, personDTO.getId());
        try {
            person = editPersonPhone(personDTO.getId(), personDTO.getPhones());
            person = editPersonHobby(personDTO.getId(), personDTO.getHobbies());
            person.setName(personDTO.getName());
            person.setBirthday(personDTO.getBirthday());
            person.setEmail(personDTO.getEmail());
            person.setGender(personDTO.getGender());
            person.setAddress(new Address(personDTO.getStreet(), new CityInfo(personDTO.getZipCode())));
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public PersonDTO findPersonByPhone(String number) {
        EntityManager em = getEntityManager();
        Phone p = em.find(Phone.class, number);
        Person person = p.getPerson();
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

    public PersonDTO getPerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();

        try {
            Person person = em.find(Person.class, id);

            if (person == null) {
                throw new PersonNotFoundException(String.format("Person with id: (%d) not found.", id));
            } else {
                PersonDTO personDTO = new PersonDTO(person);

                return personDTO;
            }
        } finally {
            em.close();
        }
    }

    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            return new PersonsDTO(em.createQuery("SELECT p FROM Person p", Person.class).getResultList());
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
