/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Phone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author marcg
 */
public class Test {
    
    private static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        
        Phone phone = new Phone("42494060", "+45");
        
        em.getTransaction().begin();
        em.persist(phone);
        em.getTransaction().commit();
    }
}
