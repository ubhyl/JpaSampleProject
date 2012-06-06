package customer.p1;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer1{

	@Id
	private long custId;
	private String firstName;
	private String lastName;
	
	
	public Customer1(){}
	
	public Customer1(String firstName, String lastName,long custId) {
		this.custId = custId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public long getCustId() {
		return custId;
	}
	public void setCustId(long custId) {
		this.custId = custId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
