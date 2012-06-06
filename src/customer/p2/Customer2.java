package customer.p2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CUSTOMERS")
public class Customer2 {

	@Id
	@SequenceGenerator(name="CustSeq", sequenceName="CUSTOMERSEQUENCE", allocationSize=10)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CustSeq")
	@Column(name = "CUSTOMERID")
	private long custId;
	
	@Column(name="FIRSTNAME", length=50, unique=false, nullable=false)
	private String firstName;
	
	@Column(name="LASTNAME", length=50, unique=false, nullable=false)
	private String lastName;
	
	public Customer2(){}
	
	public Customer2(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Customer2(long custId, String firstName, String lastName) {
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

	public String toString() {
		return "Customer2 [custId=" + custId + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
}
