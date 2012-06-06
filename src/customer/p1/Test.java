package customer.p1;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Test {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cstmr1");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Customer1 c = new Customer1("Ali", "Telli",3);
		tx.begin();
		em.persist(c);	
		tx.commit();
		em.close();
		
		
	}
}
