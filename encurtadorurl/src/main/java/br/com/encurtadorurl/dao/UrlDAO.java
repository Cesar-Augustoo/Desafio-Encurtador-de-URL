/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.encurtadorurl.dao;

import br.com.encurtadorurl.entity.Url;
import br.com.encurtadorurl.util.EntityManagerUtil;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author cesar
 */
public class UrlDAO {
    public void salvar(Url url) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(url);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Url> listar() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<Url> query
                    = em.createQuery(
                            "select u from Url u order by u.id desc",
                            Url.class
                    );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Url buscarPorCodigo(String codigo) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            TypedQuery<Url> query = em.createQuery(
                    "select u from Url u where u.codigo = :codigo",
                    Url.class
            );
            query.setParameter("codigo", codigo);
            List<Url> lista = query.getResultList();
            return lista.isEmpty() ? null : lista.get(0);
        } finally {
            em.close();
        }
    }
    
    public boolean existeCodigo(String codigo) {
        EntityManager em = EntityManagerUtil.getEntityManager();

        try {
            Long qtd = em.createQuery(
                    "select count(u) from Url u where u.codigo = :codigo",
                    Long.class
            )
                    .setParameter("codigo", codigo)
                    .getSingleResult();

            return qtd > 0;
        } finally {
            em.close();
        }
    }
    
    public void excluirTodas() {
        EntityManager em = EntityManagerUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.createQuery("delete from Url")
                    .executeUpdate();

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
