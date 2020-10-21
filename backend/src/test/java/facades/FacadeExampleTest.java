package facades;

import dto.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
<<<<<<< Updated upstream
=======
import exceptions.MissingInputException;
>>>>>>> Stashed changes
import java.sql.Date;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
<<<<<<< Updated upstream
import org.glassfish.grizzly.http.util.Header;
=======
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
    private static Person p1, p2, p3;
>>>>>>> Stashed changes
    private static PersonDTO pDTO, pDTO2, pDTO3;

    public FacadeExampleTest() {
    }

    @BeforeAll
    public static void setUpClass() {
<<<<<<< Updated upstream
=======
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getFacadeExample(emf);
>>>>>>> Stashed changes

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

        Person p1 = new Person(Date.valueOf("2020-10-10"), "Poul Madsen", "Mand", "mail.mail.com");
        Person p2 = new Person(Date.valueOf("1997-01-01"), "Torben", "Mand", "mail.mail.com");
        Person p3 = new Person(Date.valueOf("2009-10-10"), "Preben", "Kvinde", "mail.mail.com");
        Phone ph1 = new Phone("12345678", "+45");
        Phone ph2 = new Phone("12345698", "+45");
        Phone ph3 = new Phone("12345612", "+45");
        Hobby hobby1 = new Hobby("Kunstskøjteløb", "null", "top", "udendørs");
        Hobby hobby2 = new Hobby("badminton", "null", "top", "indendørs");
        Hobby hobby3 = new Hobby("skining", "null", "top", "indendørs");
        Address a1 = new Address("Flemmingvej");
        Address a2 = new Address("Prebenvej");
        Address a3 = new Address("Torstenvej");
        CityInfo c1 = new CityInfo("8732");
        CityInfo c2 = new CityInfo("8000");
        CityInfo c3 = new CityInfo("2400");
        a1.setCityInfo(c1);
        a2.setCityInfo(c2);
        a3.setCityInfo(c3);
        p1.addPhone(ph1);
        p2.addPhone(ph2);
        p3.addPhone(ph3);
        p1.setAddress(a1);
        p2.setAddress(a2);
        p3.setAddress(a3);

        p1.addHobby(hobby1);
        p2.addHobby(hobby2);
        p3.addHobby(hobby3);

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
<<<<<<< Updated upstream
    public void testAFacadeMethod() {

=======
    public void testAddPerson() throws MissingInputException {
        Person p1 = new Person(Date.valueOf("2000-01-01"), "Peter Madsen", "Mand", "ubåd@mail.com");
        Phone ph1 = new Phone("12345678", "+45");
        Hobby hobby1 = new Hobby("Kunstskøjteløb", "null", "top", "udendørs");
        Address a2 = new Address("Prebenvej");
        CityInfo c2 = new CityInfo("8000");
        p1.addPhone(ph1);
        a2.setCityInfo(c2);
        p1.setAddress(a2);
        PersonDTO pdto = new PersonDTO(p1);
        pdto.addID(hobby1.getId());
        EntityManagerFactory _emf = null;
        PersonFacade instance = PersonFacade.getFacadeExample(_emf);
        PersonDTO result = instance.addPerson(pdto);
        PersonDTO expResult = new PersonDTO(p1);
        assertEquals(expResult, result);
            
>>>>>>> Stashed changes
    }

}
