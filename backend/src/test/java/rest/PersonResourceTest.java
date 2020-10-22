package rest;

import dto.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import facades.PersonFacade;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.sql.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private static PersonFacade facade;
    private static Person p1, p2, p3;
    private static PersonDTO pDTO, pDTO2, pDTO3;
    private static Hobby h1, h2, h3;
    
    
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        EntityManager em = emf.createEntityManager();
        
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;


        Hobby h1 = new Hobby("Kunstskøjteløb", "null", "top", "udendørs");
        Hobby h2 = new Hobby("badminton", "null", "top", "indendørs");
        Hobby h3 = new Hobby("skidning", "null", "top", "indendørs");
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
    public static void closeTestServer(){
        //System.in.read();
         //Don't forget this, if you called its counterpart in @BeforeAll
         EMF_Creator.endREST_TestWithDB();
         httpServer.shutdownNow();
    }
    
    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
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
         EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();
                em.createQuery("Delete from Phone").executeUpdate();
                em.createQuery("Delete from Person").executeUpdate();
                em.createQuery("Delete from Address").executeUpdate();               
            em.getTransaction().commit();
    }
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }
   
    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
        .contentType("application/json")
        .get("/person/").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("msg", equalTo("Hello World"));   
    }
    
    @Test
    public void testGetAllPersons() throws Exception {
        given()
        .contentType("application/json")
        .get("/person/all").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("count", equalTo(2));   
    }
}
