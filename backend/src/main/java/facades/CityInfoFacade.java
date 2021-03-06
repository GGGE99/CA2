/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.CityInfosDTO;
import entities.CityInfo;
import exceptions.PersonNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author baske
 */
public class CityInfoFacade {

    private static CityInfoFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CityInfoFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CityInfoFacade getCityInfoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityInfoFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CityInfosDTO getAllZips() {
        EntityManager em = getEntityManager();
        try {
            List<CityInfo> cityInfos = (em.createQuery("SELECT c FROM CityInfo c", CityInfo.class).getResultList()); 
            CityInfosDTO cityInfosDTO = new CityInfosDTO(cityInfos);
            return cityInfosDTO;
        } finally {
            em.close();
        }
    }

    public CityInfo getCityInfo(String zipCode) throws PersonNotFoundException {
        EntityManager em = getEntityManager();

        try {
            CityInfo cityInfo = em.find(CityInfo.class, zipCode);
            if (cityInfo == null) {
                throw new PersonNotFoundException(String.format("city with zip: (%d) not found.", zipCode));
            } else {
                return cityInfo;
            }
        } finally {
            em.close();
        }
    }
}
