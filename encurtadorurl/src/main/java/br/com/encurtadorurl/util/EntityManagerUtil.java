/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.encurtadorurl.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author cesar
 */
public class EntityManagerUtil {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("encurtadorPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
