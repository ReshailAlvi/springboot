package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.beans.AddResponse;
import com.example.demo.beans.CustomerPojo;
import com.example.demo.controllers.CustomerController;
import com.example.demo.services.CustomerService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes={CustomerControllerTests.class})
public class CustomerControllerTests {

	@Mock
	CustomerService service;
	
	@InjectMocks
	CustomerController controller;
	
	CustomerPojo customer= new CustomerPojo();
	AddResponse response= new AddResponse();
	
	public List<CustomerPojo> createData(){
		List<CustomerPojo> mycustomers = new ArrayList<CustomerPojo>();
		mycustomers.add(new CustomerPojo(2,"Test user","my address",12345));
		mycustomers.add(new CustomerPojo(3,"Test user 2","my new address",6780));
		
		return mycustomers;
	}
	
	@BeforeEach
	public void init() {
		 customer = new CustomerPojo(1,"Faizan","lives in gulshan",4646);
		 response = new AddResponse(1,"Deleted successfully");
	}
	
	@Test
	@Order(1)
	public void test_getAllCustomers() {
		
		List<CustomerPojo> mockdata = createData();
		
		when(service.getallcustomers()).thenReturn(mockdata);
		controller.NavigateToHome();
	
		assertEquals(2,controller.NavigateToHome().size());
		
	}

	
	@Test
	@Order(2)
	public void test_getCustomerById() {

		int id = 1;
		
		when(service.getCustomerById(id)).thenReturn(customer);
		ResponseEntity<CustomerPojo> res= controller.getCustomerById(id);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(id,res.getBody().getId());	
		
	}
	
	@Test
	@Order(3)
	public void test_getCustomerByName() {

		String name = "Faizan";
		
		when(service.getCustomerByName(name)).thenReturn(customer);
		ResponseEntity<CustomerPojo> res= controller.getCustomerByName(name);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(name,res.getBody().getCustomerName());	
		
	}
	
	@Test
	@Order(4)
	public void test_addcustomer() {
		
		when(service.addCustomer(customer)).thenReturn(customer);
		ResponseEntity<CustomerPojo> res= controller.addCustomer(customer);
		assertEquals(HttpStatus.CREATED,res.getStatusCode());
		assertEquals(customer,res.getBody());	
		
	}

	@Test
	@Order(5)
	public void test_updatecustomer() {
		
		int id=1;
		when(service.getCustomerById(1)).thenReturn(customer);
		when(service.updateCustomer(customer)).thenReturn(customer);
		ResponseEntity<CustomerPojo> res= controller.updateCustomer(id,customer);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(id,res.getBody().getId());	
		assertEquals("Faizan",res.getBody().getCustomerName());
	}
	
	@Test
	@Order(6)
	public void test_deletecustomer() {
		
		int id=1;
		String responseMessage = "Deleted successfully";
		
		when(service.deleteCustomer(id)).thenReturn(response);
		AddResponse response= controller.deleteCustomer(id);
		assertEquals(responseMessage,response.getMessage());

	}
	
}