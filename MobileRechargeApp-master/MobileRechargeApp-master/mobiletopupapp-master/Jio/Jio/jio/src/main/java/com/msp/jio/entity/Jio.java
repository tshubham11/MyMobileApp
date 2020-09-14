
package com.msp.jio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Jio {
	
@Id @GeneratedValue(strategy=GenerationType.AUTO) private Integer iD;
@Column(name="mobile_number", nullable=false, unique=true) private String mobileNumber;
@Column (nullable=false) private Double balance;
/**
 * Default Constructor
 */
public Jio() {}
/**
 * Secondary Constructor
 * @param mobileNumber
 */
public Jio(String mobileNumber) {
	this(mobileNumber,0.00);
}
/**
 * Primary Constructor
 * @param mobileNumber
 * @param balance
 */
public Jio(String mobileNumber, Double balance) {
	this.mobileNumber=mobileNumber;
	this.balance=balance;
}
/**
 * Recharges the number of the user by increasing it's balance by the value amount
 * @param amount the amount by which the balance needs to be increased
 * @return
 */
public Jio topUp(Double amount) {
	return new Jio(this.mobileNumber,this.balance+amount);
}
/**
 * @return mobile number of the user
 */
public String getMobileNumber() {
	return mobileNumber;
}

/**
 * @return balance of the user
 */
public Double getBalance() {
	return balance;
}



}
