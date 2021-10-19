package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.beans.CustomerPojo;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.services.CustomerService;

@SpringBootTest(classes= {CustomerServiceTests.class})
public class CustomerServiceTests {

	
	@Mock
	CustomerRepository repo;
	
	@InjectMocks
	CustomerService service;
	
	private CustomerPojo customer = new CustomerPojo();
	
	public List<CustomerPojo> createData(){
		List<CustomerPojo> customers = new ArrayList<CustomerPojo>();
		customers.add(new CustomerPojo(2,"Test user","my address",12345));
		customers.add(new CustomerPojo(3,"Test user 2","my new address",6780));
		
		return customers;
	}
	
	@BeforeEach
	public void init() {
	    customer = new CustomerPojo(1,"Faizan","lives in gulshan",4646);
	}
	
	@Test
	public void test_getallcustomers() {
		
		List<CustomerPojo> mockdata = createData();
		
		when(repo.findAll()).thenReturn(mockdata);
		service.getallcustomers();
		
		assertEquals(2,service.getallcustomers().size());	
	}
	
	@Test
	public void test_getcustomerbyid() {
		
		int id = 1;
		
		when(repo.findById(id)).thenReturn(Optional.of(customer));
		
		service.getCustomerById(id);
		assertEquals(id,service.getCustomerById(1).getId());
		
	}
	
	@Test
	public void test_getcustomerbyname() {
		String name ="Test user 2";
		List<CustomerPojo> mockdata = createData();
		
		when(repo.findAll()).thenReturn(mockdata);
		service.getCustomerByName(name);
		
		assertEquals(name,service.getCustomerByName(name).getCustomerName());
		
	}

	@Test
	public void test_addcustomer() {
		when(repo.save(customer)).thenReturn(customer);
		assertEquals(customer,service.addCustomer(customer));
	}
	
	@Test
	public void test_updatecustomer() {
		when(repo.save(customer)).thenReturn(customer);
		assertEquals(customer,service.updateCustomer(customer));
	}
	
	@Test
	public void test_deletecustomer() {
		service.deleteCustomer(customer.getId());
		verify(repo,times(1)).deleteById(customer.getId());		
	}
}
