package com.example.demo.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class CustomerPojo {
	
	@Id
	@Column(name="id")
	int id;
	
	@Column(name="phonenumber")
	int phoneNumber;
	
	@Column(name="customername")
	String customerName;
	
	@Column(name="address")
	String customerAddress;
	
	CustomerPojo(int i,String n,String a,int ph){
		this.id = i;
		this.customerName = n;
		this.customerAddress = a;
		this.phoneNumber = ph;
	}
	
	public CustomerPojo(){}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	
	
	
}
