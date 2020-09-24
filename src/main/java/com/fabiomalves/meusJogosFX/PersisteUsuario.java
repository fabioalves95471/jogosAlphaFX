package com.fabiomalves.meusJogosFX;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersisteUsuario {

	EntityManagerFactory emf;
	EntityManager em;
	
	PersisteUsuario () {
		emf = Persistence.createEntityManagerFactory("usuarioPersistence");
		em = emf.createEntityManager();
	}
	
	public void salvar (Usuario1 usuario) {
		em.getTransaction().begin();
		em.merge(usuario);
		em.getTransaction().commit();
		emf.close();
	}
}
