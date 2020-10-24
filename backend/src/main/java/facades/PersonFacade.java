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
import exceptions.InvalidInputException;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.ArrayList;
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

    public Address getPersonAddress(String street, String zipCode) {
        EntityManager em = getEntityManager();
        Address address = null;
        try {
            Query query = em.createQuery("SELECT a FROM Address a where a.street = :street");
            query.setParameter("street", street);
            address = (Address) query.getSingleResult();

        } catch (Exception e) {
        }

        if (address == null) {
            Query query2 = em.createQuery("SELECT c FROM CityInfo c WHERE c.zipCode = :zipcode");
            query2.setParameter("zipcode", zipCode);
            CityInfo cityInfo = (CityInfo) query2.getSingleResult();
            address = new Address(street, cityInfo);
            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
            return address;
        } else {
            return address;
        }
    }

    public Phone getPhoneBuNumber(String number) {
        EntityManager em = getEntityManager();
        Phone phone = null;
        try {
            Query query = em.createQuery("SELECT p FROM Phone p where p.number = :number");
            query.setParameter("number", number);
            phone = (Phone) query.getSingleResult();
            return phone;
        } catch (Exception e) {
//            System.out.println(e);
        }
        return phone;
    }

    public PersonDTO addPerson(PersonDTO personDTO) throws InvalidInputException, PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person person = new Person(personDTO);

        try {
            person.setAddress(getPersonAddress(personDTO.getStreet(), personDTO.getZipCode()));
            for (int i : personDTO.getHobbiesID()) {
                person.addHobby(em.find(Hobby.class, i));
            }
            for (PhoneDTO p : personDTO.getPhones()) {
                if (getPhoneBuNumber(p.getNumber()) == null) {
                    person.addPhone(new Phone(p.getNumber(), p.getDescription()));
                } else {
                    throw new InvalidInputException(String.format("Phone number: (%s) is taken", p.getNumber()));
                }
            }

            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
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

    public PersonDTO editPerson(PersonDTO personDTO) throws MissingInputException, InvalidInputException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, personDTO.getId());

        try {

            person.deleteHobbies();

            List<Integer> idList = new ArrayList();
            for (int id : personDTO.getHobbiesID()) {
                person.addHobby(em.find(Hobby.class, id));
            }
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
                System.out.println(phoneDTO.getNumber());
                if (getPhoneBuNumber(phoneDTO.getNumber()) == null) {
                    person.addPhone(new Phone(phoneDTO.getNumber(), phoneDTO.getDescription()));
                    Phone phone = new Phone(phoneDTO.getNumber(), phoneDTO.getDescription());
                    addPhone(person, phone);
                } else {
                    throw new InvalidInputException(String.format("Phone number: (%s) is taken", phoneDTO.getNumber()));
                }
            }

            person.setAddress(getPersonAddress(personDTO.getStreet(), personDTO.getZipCode()));

            person.setName(personDTO.getName());
            person.setBirthday(personDTO.getBirthday());
            person.setEmail(personDTO.getEmail());
            person.setGender(personDTO.getGender());

            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();

            System.out.println(person);
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public PersonDTO findPersonByPhone(String number) {
        EntityManager em = getEntityManager();
        Phone p = em.find(Phone.class,
                number);
        Person person = p.getPerson();
        return new PersonDTO(person);
    }

    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class,
                id);
        if (person == null) {
            throw new PersonNotFoundException(String.format("Person with id: (%d) not found", id));
        } else {
            try {
                em.getTransaction().begin();
                for (Phone phone : person.getPhones()) {
                    em.remove(em.find(Phone.class,
                            phone.getNumber()));
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
            Person person = em.find(Person.class,
                    id);

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
            return new PersonsDTO(em.createQuery("SELECT p FROM Person p", Person.class
            ).getResultList());
        } finally {
            em.close();
        }
    }
    
    public PersonsDTO getPersonsByZipcode(String zipcode) throws InvalidInputException {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.address.cityInfo.zipCode = :zipcode", Person.class);
            query.setParameter("zipcode", zipcode);
            List<Person> persons = query.getResultList();
            PersonsDTO personsDTO = new PersonsDTO(persons);
            if (personsDTO.getAll().isEmpty()) {
                throw new InvalidInputException(String.format("zipcoden: (%s) har ingen personer tilkyntet", zipcode));
            }
            return personsDTO;

        } catch (Exception e) {
            throw new InvalidInputException(String.format("zipcoden: (%s) er ikke en gyldig zipcode", zipcode));
        }
    }
    
    public PersonsDTO getPersonsByHobbyId(int id) throws InvalidInputException {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE :hobby MEMBER OF p.hobbies", Person.class);
            
            query.setParameter("hobby", em.find(Hobby.class, id));
            List<Person> persons = query.getResultList();
            PersonsDTO personsDTO = new PersonsDTO(persons);
            if (personsDTO.getAll().isEmpty()) {
                throw new InvalidInputException(String.format("Hobby: (%d) har ingen personer tilkyntet", id));
            }
            return personsDTO;

        } catch (Exception e) {
            throw new InvalidInputException(String.format("Hobby: (%s) findes ikke", id));
        }
    }

}
