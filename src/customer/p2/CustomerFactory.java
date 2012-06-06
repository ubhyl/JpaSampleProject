package customer.p2;

import java.util.*;

public class CustomerFactory {
	private static Random random = new Random();
	private static boolean customerTypeFlag = false;
	
	private static String[] firstnames = {"Ali", "Ayse", "Bahar", "Cem", "Demet", "Eylem", "Faruk", "Fatma",
			               "Ganime", "Haydar", "Halil", "Ismail", "Jale", "Kemal", "Leman", "Mustafa",
			               "Nedim", "Nilufer", "Selim", "Selman", "Sevda", "Tarik", "Teoman", 
			               "Yeliz", "Zuhal"};
	private static String[] lastnames = {"Arabaci", "Aslan", "Batur", "Bulut", "Ceviz", "Karli", "Lale", "Ozturk", 
			                      "Pasa", "Sari", "Serim", "Telli", "Torbaci", "Yazar", "Zafer"};
	
	
	public static Customer2 createCustomer(){
		Customer2 customer = new Customer2();
		customer.setFirstName(createFirstName());
		customer.setLastName(createLastName());
		
		return customer;
	}
	
	private static String createFirstName(){
		int randomInt = random.nextInt(25);
		return firstnames[randomInt];
	}
	
	private static String createLastName(){
		int randomInt = random.nextInt(15);
		return lastnames[randomInt];
	}
}
