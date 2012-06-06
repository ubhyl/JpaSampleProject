package customer.p2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;



public class Test {

	private EntityManagerFactory emf;
	
	public Test(){
		emf = Persistence.createEntityManagerFactory("customer2");
	}

	public static void main(String[] args) {
		
		Test test = new Test();
		test.createACustomer();
//		test.createACustomerWithoutTransaction();
//		test.createCustomers(100);
//		test.createAndManageCustomer();
//		test.retrieveACustomerWithID(25);
//		test.retrieveAReadOnlyCustomerWithID(25);
//		test.removeACustomer(25);
//		test.containsACustomer();
//		test.detachACustomer();
//		test.flush();
//		test.lifeCycle();
//		test.rollback();
	}

	/*
	 * void persist(Object entity) ornegi. VT'de 1 tane nesne olusturur.
	 */
	void createACustomer() {
		System.out.println("\nCreating a customer:");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Customer2 c1 = new Customer2("Ali", "Telli");
		tx.begin();
		em.persist(c1);
		tx.commit();
		em.close();
	}
	
	/*
	 * void persist(Object entity) ornegi. VT'de 5 tane nesne olusturur.
	 */
	void createCustomers() {
		System.out.println("\nCreating 5 customers:");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Customer2 c1 = new Customer2("Ali", "Telli");
		Customer2 c2 = new Customer2("Serap", "Atik");
		Customer2 c3 = new Customer2("Kemal", "Tanir");
		Customer2 c4 = new Customer2("Betul", "Kara");
		Customer2 c5 = new Customer2("Selman", "Saki");
		tx.begin();
		em.persist(c1);
		em.persist(c2);
		em.persist(c3);
		em.persist(c4);
		em.persist(c5);
		tx.commit();
		em.close();
	}

	/*
	 * Islemsiz (transactionsiz, TX olmadan) void persist(Object entity) ornegi. VT'da nesne olusturmaz.
	 */
	void createACustomerWithoutTransaction() {
		System.out.println("\nCreating a customer without a transaction:");
		EntityManager em = emf.createEntityManager();
		Customer2 c1 = new Customer2("Ali", "Telli");
		em.persist(c1);
		em.close();
	}

	/*
	 * Pek cok entity ile void persist(Object entity) ornegi. VT'de gecilen sayi kadar random nesne olusturur.
	 * Nesneler CustomerFactory'den alınır.
	 */
	void createCustomers(int n) {
		System.out.println("\nCreating " + n + " customers:");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Customer2 customer = null;
		for (int i = 0; i < n; i++) {
			customer = CustomerFactory.createCustomer();
			em.persist(customer);
		}
		tx.commit();
		em.close();
	}
	
	/*
	 * Kalicilik baglami (Persistent context) ornegi. Once bir nesne olusturuyor ve VT'ye gonderiyor.
	 * Soyadi "Cemil" olan nesneyi daha sonra bir TX ile degistiriyor ve TX'i commit ediyor. 
	 * VT'de nesnenin soyismi de degisti.
	 */
	void createAndManageCustomer() {
		System.out.println("\nCreating and managing a customer:");

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Customer2 c = new Customer2("Sami", "Cemil");
		tx.begin();
		em.persist(c);
		tx.commit();
		System.out.println(c);
		
		tx = em.getTransaction();
		tx.begin();
		c.setLastName("Kamil");
		tx.commit();
		System.out.println(c);
		em.close();
	}
	
	/*
	 * Gecilen bir id'ye sahip nesneyi VT'den getirir.
	 */
	public void retrieveACustomerWithID(long id) {
		System.out.println("\nCustomer with id " + id);
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Customer2 customer = em.find(Customer2.class, id);
		if(customer == null){
			System.out.println("\nNo such customer with id: " + id);
			return;
		}
		System.out.println(customer);
		tx.commit();
		em.close();
	}
	
	/*
	 * Gecilen bir id'ye sahip nesneyi VT'den getirir. Bir TX olmadigi icin 
	 * getirilen nesne read onlydir yani nesneye yapilan degisiklikler VT'ye gonderilmez.
	 * Ancak bir TX baslatilip commit edilirse degisiklikler VT'ye gonderilir.
	 */
	public void retrieveAReadOnlyCustomerWithID(long id) {
		System.out.println("\nRead Only Customer with id " + id);
		EntityManager em = emf.createEntityManager();
		Customer2 customer1 = em.find(Customer2.class, id);
		if(customer1 == null){
			System.out.println("\nNo such customer with id: " + id);
			return;
		}
		System.out.println(customer1);
		customer1.setLastName("XXX");
		System.out.println(customer1);
		System.out.println("Does persistent context have customer1? " + em.contains(customer1));
		
		// Asagidaki satirlari actiginizda soyisim XXX olarak degistirilecektir.
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
//		tx.commit();
		em.close();
		
		EntityManager em2 = emf.createEntityManager();
		Customer2 customer2 = em2.find(Customer2.class, id);
		System.out.println(customer2);
		em2.close();		
	}

	/*
	 * Gecilen id'ye sahip nesneyi VT'den siler.
	 */
	public void removeACustomer(long id) {
		System.out.println("\nRemoving the customer: " + id);
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Customer2 customer = em.find(Customer2.class, id);
		if(customer == null){
			System.out.println("\nNo such customer with id: " + id);
			return;
		}
		System.out.println("\nCustomer info: " + customer);
		em.remove(customer);
		tx.commit();
		em.close();
	}

	/*
	 * contains() metotunu cagirir.
	 */
	public void containsACustomer() {
		System.out.println("\nChecking if a customer object is in persistent context");

		EntityManager em = emf.createEntityManager();
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
		Customer2 customerRetrieved = em.find(Customer2.class, (long)10);
		System.out.println(customerRetrieved);
		Customer2 newCustomer = new Customer2(customerRetrieved.getCustId(), 
											  customerRetrieved.getFirstName(), 
											  customerRetrieved.getLastName());
		System.out.println(newCustomer);
		System.out.println("Does persistent context have newCustomer? " + em.contains(newCustomer));
		System.out.println("Does persistent context have customerRetrieved? " + em.contains(customerRetrieved));
		em.remove(customerRetrieved);
		System.out.println("Does persistent context have customerRetrieved? " + em.contains(customerRetrieved));
//		tx.commit();
		em.close();
	}
	
	/*
	 * Customer2 nesnesinin kopararilmis (detached) hale getirilmesi ve merge metotu.
	 */
	public void detachACustomer() {
		System.out.println("\nDetaching a customer");

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Customer2 customer= em.find(Customer2.class, (long)90);
		System.out.println(customer);
		
		tx.commit(); 

		// customer koparilmis(detached) durumda. Yapilan degisiklikleri VT'ye yazmak icin
		// merge yapilmali.
		customer.setLastName("Detached");
		System.out.println("Does persistent context have customer? " + em.contains(customer));
		em.close();
		
		em = emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin();
		em.merge(customer);
		tx.commit();
		
		Customer2 retrievedCustomer = em.find(Customer2.class, (long)90);
		System.out.println(retrievedCustomer);
	
		em.close();
	}
	
	/**
	 * Flush metodunun kullanimi. Transactionin commit ve rollback durumlarina bakin.
	 */
	public void flush(){
		System.out.println("\nCreating a customer by flush:");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Customer2 c1 = new Customer2("Selim", "Aslan");
		tx.begin();
		em.persist(c1);
		long id = c1.getCustId();
		System.out.println("Its id is : " + id);
		em.flush();
		System.out.println("Flushed to the db.");
		Customer2 retrievedCustomer1 = em.find(Customer2.class, id);
		System.out.println(retrievedCustomer1);
		
		// Eger commit yerine rollback yapilirsa daha asagida retrievedCustomer2 null gelir.
		tx.commit();
//		tx.rollback();
		
		Customer2 retrievedCustomer2 = em.find(Customer2.class, id);
		System.out.println(retrievedCustomer2);
		em.close();
	}
	
	/**
	 * Customer2 nesnesinin hayat dongusunu gosterir
	 */
	public void lifeCycle(){
		System.out.println("\nLife cycle of a customer:");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		//c1 simdi new durumunda, VT'de henuz kalici degil.
		Customer2 c1 = new Customer2("Life", "Cycle");
		tx.begin();
		em.persist(c1);
		tx.commit();
		
		//c1 simdi managed durumunda, VT'de kalici ve durum degisiklikleri em tarafından takip ediliyor.
		// Ilk commit'te degisen durum VT'ye yansitilacaktir.
		c1.setLastName("YYY");
		tx = em.getTransaction();
		tx.begin();
		tx.commit();
		em.close();
		
		// c1 simdi detached. yapilan degisiklikler takip edilmiyor cunku em kapandi.
		c1.setLastName("Detached");
		// Degisiklikleri kalici kilmak icin merge cagrilmali
		em = emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin();
		Customer2 c2 = em.merge(c1);
		tx.commit();
		// Simdi c2 managed durumda, VT'de kalici hale getirilen yeni durum c2'de cunku.
		//c1 halen detached ve degisiklikleri takip edilmiyor, dolayisiyla onu kullanmayin.
		em.close();
		
		em = emf.createEntityManager();
		Customer2 c3 = em.find(Customer2.class, c2.getCustId());
		if(c3 == null){
			System.out.println("\nNo such customer with id: " + c2.getCustId());
			return;
		}
		//c3 burada da managed durumda, degisiklikler takip ediliyor.
		c3.setLastName("Detached3");
		tx = em.getTransaction();
		tx.begin();
		tx.commit();
		//Degisiklikler VT'de
		
		tx = em.getTransaction();
		System.out.println("Removing: " + c3);
		em.remove(c3);
		//c3 simdi removed durumunda
		// rollback ya da persist cagrisiyla tekrar VT'ye yazilir.
		tx.begin();
		tx.commit();
		em.close();
		//c3 simdi new durumunda.
		//VT'e tekrar yazmak icin persist'e ihtiyac var.
		
		// Yeni bir em ve tx olusturalim
		em = emf.createEntityManager();
		tx = em.getTransaction();
		//Yeni bir Customer2 entity nesnesi, c4 ve persist'i cagirip DB'ye yazalim.
		Customer2 c4 = new Customer2("C4Life", "Cycle");
		tx.begin();
		em.persist(c4);
		tx.commit();
		
		//Simdi em'i clear edelim
		em.clear();
		//c4 simdi detached hale geldi
		c4.setLastName("Detached4");
		System.out.println(c4);
		// c4'un son halini VT'ye yazmak icin merge edilmeli
		tx = em.getTransaction();
		tx.begin();
		em.merge(c4);
		tx.commit();
		em.close();
	}
	
	public void rollback(){
		System.out.println("\nRollback:");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		//c1 simdi new durumunda, VT'de henuz kalici degil.
		Customer2 c1 = new Customer2("Roll", "Back");
		tx.begin();
		// Once kalici kiliyoruz.
		em.persist(c1);
		em.flush();
		// Simdi ise geri aliyoruz.
		tx.rollback();
		em.close();
		
		//c1 VT'de degil.
		em = emf.createEntityManager();
		System.out.println(c1.getCustId());
		Customer2 c2 = em.find(Customer2.class, c1.getCustId());
		if(c2 == null)
			System.out.println("VT'de boyle bir nesne yok.");
	}
}
