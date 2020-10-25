/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.HobbiesDTO;
import dto.HobbyDTO;
import entities.Hobby;
import exceptions.InvalidInputException;
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
public class HobbyFacade {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HobbyFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long countPersonWithHobbyID(int id) throws InvalidInputException {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT COUNT(p) FROM Person p WHERE :hobby MEMBER OF p.hobbies");
            Hobby hobby = em.find(Hobby.class, id);
            if (hobby == null) {
                throw new InvalidInputException(String.format("Hobby: (%d) findes ikke", id));
            } else {
                query.setParameter("hobby", hobby);
                long hobbyCount = (long) query.getSingleResult();
                return hobbyCount;
            }
        } finally {
            em.close();
        }
    }

    public HobbyDTO getHobby(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();

        try {
            HobbyDTO hobby = new HobbyDTO(em.find(Hobby.class, id));
            if (hobby == null) {
                throw new PersonNotFoundException(String.format("Hobby with id: (%d) not found.", id));
            } else {
                return hobby;
            }
        } finally {
            em.close();
        }
    }

    public HobbiesDTO allHobbies() {
        EntityManager em = getEntityManager();
//        List<HobbyDTO> listHobbies = new ArrayList<>();

        try {
            Query query = em.createQuery("SELECT h FROM Hobby h");
            List<Hobby> listHobbies = query.getResultList();
            return new HobbiesDTO(listHobbies);

        } catch (Exception e) {
            //catch fejl her
        } finally {

            em.close();
        }
        return null;
    }

}
