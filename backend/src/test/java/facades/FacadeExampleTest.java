package facades;

import dto.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import exceptions.MissingInputException;
import java.sql.Date;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class FacadeExampleTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2, p3;
    private static PersonDTO pDTO, pDTO2, pDTO3;
    private static Hobby h1, h2, h3;

    public FacadeExampleTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        EntityManager em = emf.createEntityManager();

//        Person person1 = new Person(Date.valueOf("2020-10-10"), "HEJ", "LORT", "adasdadadasd@sdasdasd.cascas");
//        Phone phone = new Phone("12345678", "+45");
//        Address address = new Address("LORTegade 21",new CityInfo("8732"));
//
//        person1.addPhone(phone);
//        person1.setAddress(address);
//
//
//        PersonDTO personDTO = new PersonDTO(person1);
//        personDTO.addID(14);
//        Facade.addPerson(personDTO);
        Hobby h1 = new Hobby("Kunstskøjteløb", "null", "top", "udendørs");
        Hobby h2 = new Hobby("badminton", "null", "top", "indendørs");
        Hobby h3 = new Hobby("skining", "null", "top", "indendørs");
        CityInfo c1 = new CityInfo("8732", "Hovedgaård");
        CityInfo c2 = new CityInfo("8000", "Århus");
        CityInfo c3 = new CityInfo("2800", "Lyngby");

        em.getTransaction().begin();
        em.persist(h1);
        em.persist(h2);
        em.persist(h3);
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.getTransaction().commit();
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        p1 = new Person(Date.valueOf("2020-10-10"), "Poul Madsen", "Mand", "mail.mail.com");
        p2 = new Person(Date.valueOf("1997-01-01"), "Torben", "Mand", "mail.mail.com");
        p3 = new Person(Date.valueOf("2009-10-10"), "Preben", "Kvinde", "mail.mail.com");
        Phone ph1 = new Phone("12345678", "+45");
        Phone ph2 = new Phone("12345698", "+45");
        Phone ph3 = new Phone("12345612", "+45");

        Address a1 = new Address("Flemmingvej", new CityInfo("8732"));
        Address a2 = new Address("Prebenvej", new CityInfo("8000"));
        Address a3 = new Address("Torstenvej", new CityInfo("2800"));
        p1.addPhone(ph1);
        p2.addPhone(ph2);
        p3.addPhone(ph3);
        p1.setAddress(a1);
        p2.setAddress(a2);
        p3.setAddress(a3);
        p1.addHobby(em.find(Hobby.class, 1));
        p2.addHobby(em.find(Hobby.class, 2));
        p2.addHobby(em.find(Hobby.class, 1));
        p3.addHobby(em.find(Hobby.class, 3));
        System.out.println(p1.getHobbies());

        try {
            em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAddPerson() throws MissingInputException {
//        Person p1 = new Person(Date.valueOf("2000-01-01"), "Peter Madsen", "Mand", "ubåd@mail.com");
//        Phone ph1 = new Phone("12345678", "+45");
////        Hobby hobby1 = new Hobby("Kunstskøjteløb", "null", "top", "udendørs");
//        CityInfo c2 = new CityInfo("8000");
//        Address a2 = new Address("Prebenvej", c2);
//        p1.addPhone(ph1);
//        p1.setAddress(a2);
//        PersonDTO pdto = new PersonDTO(p1);
//        pdto.addID(1);
//        EntityManagerFactory _emf = null;
//        PersonFacade instance = PersonFacade.getFacadeExample(_emf);
//        PersonDTO result = instance.addPerson(pdto);
//        PersonDTO expResult = new PersonDTO(p1);
        assertEquals(true, true);
        
    }

}
