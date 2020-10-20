package facades;

import dto.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.sql.Date;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.glassfish.grizzly.http.util.Header;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class FacadeExampleTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static PersonDTO pDTO, pDTO2, pDTO3;

    public FacadeExampleTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = PersonFacade.getFacadeExample(emf);

       EntityManager em = emf.createEntityManager();

       Person p1 = new Person(Date.valueOf("2020-10-10"), "Poul Madsen", "Mand", "mail.mail.com");
       Person p2 = new Person(Date.valueOf("1997-01-01"), "Torben", "Mand", "mail.mail.com");
       Person p3 = new Person(Date.valueOf("2009-10-10"), "Preben", "Kvinde", "mail.mail.com");
       Phone ph1 = new Phone("12345678", "+45");
       Phone ph2 = new Phone("12345698", "+45");
       Phone ph3 = new Phone("12345612", "+45");
       Hobby hobby1 = new Hobby("Kunstskøjtløb", "null", "top", "udendørs");
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
       p2.addPhone(ph3);
       p1.setAddress(a1);
       p2.setAddress(a2);
       p3.setAddress(a3);

       pDTO = new PersonDTO(p1);
       pDTO2 = new PersonDTO(p2);
       pDTO3 = new PersonDTO(p3);
       pDTO.addID(hobby1.getId());
       pDTO2.addID(hobby2.getId());
       pDTO3.addID(hobby3.getId());

       try{
           em.getTransaction().begin();
           em.createQuery("DELETE from Phone").executeUpdate();
           em.createQuery("DELETE from Address").executeUpdate();
           em.createQuery("DELETE from Person").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
           em.getTransaction().commit();
       }finally{
           em.close();
       }
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() {

    }

}
