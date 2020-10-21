/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.HobbyDTO;
import entities.Hobby;
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

    public long getHobbyCount() {
        EntityManager em = getEntityManager();
        try {
            long hobbyCount = (long) em.createQuery("SELECT COUNT(r) FROM Hobby r").getSingleResult();
            return hobbyCount;
        } finally {
            em.close();
        }
    }

    public Hobby getHobby(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();

        try {
            Hobby hobby = em.find(Hobby.class, id);
            if (hobby == null) {
                throw new PersonNotFoundException(String.format("Hobby with id: (%d) not found.", id));
            } else {
                return hobby;
            }
        } finally {
            em.close();
        }
    }

    public List<HobbyDTO> allHobbies() {
        EntityManager em = getEntityManager();
        List<HobbyDTO> listHobbies = new ArrayList<>();

        try {
            Query query = em.createQuery("SELECT h FROM Hobby h");
            listHobbies = query.getResultList();
            return listHobbies;

        } catch (Exception e) {
            //catch fejl her
        } finally {

            em.close();
        }

        return listHobbies;
    }
    
    
}
