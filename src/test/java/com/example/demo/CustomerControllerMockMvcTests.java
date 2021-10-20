package com.example.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.beans.AddResponse;
import com.example.demo.beans.CustomerPojo;
import com.example.demo.controllers.CustomerController;
import com.example.demo.services.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes= {CustomerControllerMockMvcTests.class})
@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.restservices.demo")
public class CustomerControllerMockMvcTests {

	@Autowired
	MockMvc mvc;
	
	@Mock
	CustomerService service;
	
	@InjectMocks
	CustomerController controller;
	
	CustomerPojo customer_data = new CustomerPojo();
	
	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
		customer_data = new CustomerPojo(1,"Reshail","bahadurabad",44556);
	}
	
	@Test
	public void test_getallcustomers() {
		
		List<CustomerPojo> mycustomers = new ArrayList<CustomerPojo>();
		mycustomers.add(new CustomerPojo(1,"MSA user","my address",12345));
		mycustomers.add(new CustomerPojo(2,"Sada user","gulshan",5678));
		
		when(service.getallcustomers()).thenReturn(mycustomers);
		
		try {
			this.mvc.perform(get("/getallcustomers"))
				.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test_getcustomerbyid() {

		int id = 1;
		
		when(service.getCustomerById(id)).thenReturn(customer_data);
		
		try {
			this.mvc.perform(get("/getcustomer/{id}",id))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
				.andExpect(MockMvcResultMatchers.jsonPath("customerName").value("Reshail"))
				.andDo(print());		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_getcustomerbyname() {
		
		String name = "Reshail";
		
		when(service.getCustomerByName(name)).thenReturn(customer_data);
		
		try {
			this.mvc.perform(get("/getcustomerbyname/{name}",name))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("customerName").value(name))
				.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_addcustomer() throws JsonProcessingException {
		
		when(service.addCustomer(customer_data)).thenReturn(customer_data);
		
		ObjectMapper objMapper = new ObjectMapper();
		String jsonbody = objMapper.writeValueAsString(customer_data);
		
		try {
			this.mvc.perform(post("/addcustomer").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_updatecustomer() throws JsonProcessingException {
		
		int id = 1;
		when(service.getCustomerById(1)).thenReturn(customer_data);
		when(service.updateCustomer(customer_data)).thenReturn(customer_data);
		
		ObjectMapper objMapper = new ObjectMapper();
		String jsonbody = objMapper.writeValueAsString(customer_data);
		
		try {
			this.mvc.perform(put("/updatecustomer/{id}",id).content(jsonbody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("customerName").value("Reshail"))
				.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_deletecustomer() {
		
		AddResponse response= new AddResponse();
		response = new AddResponse(1,"Deleted successfully");
		int id=1;
		String responseMessage = "Deleted successfully";
		
		when(service.deleteCustomer(id)).thenReturn(response);
		
		try {
			this.mvc.perform(delete("/deletecustomer/{id}",id))
				.andExpect(MockMvcResultMatchers.jsonPath("message").value(responseMessage))
				.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
