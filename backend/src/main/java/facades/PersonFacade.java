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
//
//    public Person editPersonPhone(int personID, List<PhoneDTO> phones) {
//        EntityManager em = getEntityManager();
//        Person p = em.find(Person.class, personID);
//        System.out.println("Hej xD: " + p);
//        try {
//            em.getTransaction().begin();
//            for (Phone phone : p.getPhones()) {
//                System.out.println(phone);
//                em.remove(em.find(Phone.class, phone.getNumber()));
//                p.removePhone(phone);
//            }
//            em.merge(p);
//            em.getTransaction().commit();
//            System.out.println("Hej xD: " + p);
//        } finally {
//            editPersonAddPhone(personID, phones);
//        }
//
//        return p;
//    }
//
//    public Person editPersonAddPhone(int personID, List<PhoneDTO> phones) {
//        EntityManager em = getEntityManager();
//        Person p = em.find(Person.class, personID);
//        em.getTransaction().begin();
//        for (PhoneDTO phone : phones) {
//            p.addPhone(new Phone(phone.getNumber(), phone.getDescription()));
//        }
//        em.persist(p);
//        em.getTransaction().commit();
//        return p;
//    }

    public void deletePhone(String number) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Phone p where p.number = :number");
            query.setParameter("number", number).executeUpdate();

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void addPhone(Person person, Phone phone) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            person.addPhone(phone);
            em.getTransaction().commit();
        } finally {
            em.close();
            return;
        }

    }

    public List<String> getAllPhoneNumberFromPerson(int personID) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT p.number FROM Phone p where p.person.id = :personID");
            query.setParameter("personID", personID);
            List<String> phoneList = (List<String>) query.getResultList();
            return phoneList;
        } finally {
            em.close();
        }
    }

    public void removeHobbyFromPersonAndAddNew(Person person, List<Integer> hobbies) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        for (Hobby hobby : person.getHobbies()) {
            hobby.removePerson(person);
        }
        for (int i : hobbies) {
            person.addHobby(em.find(Hobby.class, i));
        }
        em.getTransaction().commit();

    }

    public PersonDTO editPerson(PersonDTO personDTO) throws MissingInputException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, personDTO.getId());
        try {
            List<Integer> idList = new ArrayList();
            for (Hobby hobby : person.getHobbies()) {
                idList.add(hobby.getId());
            }

            removeHobbyFromPersonAndAddNew(person, personDTO.getHobbiesID());

            for (Integer integer : idList) {
                if (!personDTO.getHobbiesID().contains(integer)) {
                }
            }

            List<String> phones = getAllPhoneNumberFromPerson(person.getId());
            List<String> numbers = new ArrayList();
            for (PhoneDTO phone : personDTO.getPhones()) {
                numbers.add(phone.getNumber());
            }
            for (String phone : phones) {
                if (!numbers.contains(phone)) {
                    deletePhone(phone);
                }
            }
            person.setPhones(new ArrayList());
            for (PhoneDTO phoneDTO : personDTO.getPhones()) {
                Phone phone = new Phone(phoneDTO.getNumber(), phoneDTO.getDescription());
                addPhone(person, phone);
            }
            em.getTransaction().begin();
            person.setName(personDTO.getName());
            person.setBirthday(personDTO.getBirthday());
            person.setEmail(personDTO.getEmail());
            person.setGender(personDTO.getGender());
            person.setAddress(new Address(personDTO.getStreet(), new CityInfo(personDTO.getZipCode())));
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

    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);
        if (person == null) {
            throw new PersonNotFoundException(String.format("Person with id: (%d) not found", id));
        } else {
            try {
                em.getTransaction().begin();
                for (Phone phone : person.getPhones()) {
                    em.remove(em.find(Phone.class, phone.getNumber()));
                    person.removePhone(phone);
                }
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

}
