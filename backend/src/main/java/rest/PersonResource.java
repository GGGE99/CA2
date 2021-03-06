/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;

import entities.Person;
import exceptions.InvalidInputException;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import facades.FacadeExample;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 *
 * @author marcg
 */
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() {
        PersonsDTO persons = FACADE.getAllPersons();
        return GSON.toJson(persons);
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonById(@PathParam("id") int id) throws PersonNotFoundException {
        PersonDTO p = FACADE.getPerson(id);
        return GSON.toJson(p);
    }

    @Path("zipcode/{zipcode}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByzipcode(@PathParam("zipcode") String zipcode) throws InvalidInputException {
        PersonsDTO p = FACADE.getPersonsByZipcode(zipcode);
        return GSON.toJson(p);
    }

    @Path("hobby/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByzipcode(@PathParam("id") int id) throws InvalidInputException {
        PersonsDTO p = FACADE.getPersonsByHobbyId(id);
        return GSON.toJson(p);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addPerson(String personDTO) throws MissingInputException, InvalidInputException, PersonNotFoundException {
        PersonDTO p = GSON.fromJson(personDTO, PersonDTO.class);
        PersonDTO pAdded = FACADE.addPerson(p);
        return GSON.toJson(pAdded);
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public String editPerson(String person, @PathParam("id") Integer id) throws PersonNotFoundException, MissingInputException, InvalidInputException {
        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        p.setId(id);

        PersonDTO pEdited = FACADE.editPerson(p);

        return GSON.toJson(pEdited);
    }
}
