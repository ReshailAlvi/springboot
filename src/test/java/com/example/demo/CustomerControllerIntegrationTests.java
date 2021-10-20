package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.demo.beans.CustomerPojo;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes= {CustomerControllerIntegrationTests.class})
public class CustomerControllerIntegrationTests {

	String expected="[\n"
			+ "    {\n"
			+ "        \"id\": 1,\n"
			+ "        \"phoneNumber\": 12345,\n"
			+ "        \"customerName\": \"Reshail\",\n"
			+ "        \"customerAddress\": \"Bahadurabad\"\n"
			+ "    },\n"
			+ "    {\n"
			+ "        \"id\": 2,\n"
			+ "        \"phoneNumber\": 3345,\n"
			+ "        \"customerName\": \"Rebail\",\n"
			+ "        \"customerAddress\": \"Gulshan\"\n"
			+ "    }\n"
			+ "]"; //Have this as part of resources 
	 
	@Test
	@Order(1)
	public void test_getallcustomers() {
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<String> response=rest.getForEntity("http://localhost:8080/getcustomers", String.class);	
		try {
			JSONAssert.assertEquals(expected,response.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	 
	@Test
	@Order(2)
	public void test_getcustomerbyid() {
		
		String data ="{\n"
				+ "    \"id\": 1,\n"
				+ "    \"phoneNumber\": 12345,\n"
				+ "    \"customerName\": \"Reshail\",\n"
				+ "    \"customerAddress\": \"Bahadurabad\"\n"
				+ "}";
		
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<String> response=rest.getForEntity("http://localhost:8080/getcustomer/1", String.class);
		try {
			JSONAssert.assertEquals(data,response.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(3)
	public void test_getcustomerbyname() {
		
		String data ="{\n"
				+ "    \"id\": 1,\n"
				+ "    \"phoneNumber\": 12345,\n"
				+ "    \"customerName\": \"Reshail\",\n"
				+ "    \"customerAddress\": \"Bahadurabad\"\n"
				+ "}";
		
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<String> response=rest.getForEntity("http://localhost:8080/getcustomerbyname/Reshail", String.class);
		try {
			JSONAssert.assertEquals(data,response.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(4)
	public void test_addcustomer() {
		
		CustomerPojo newCustomer = new CustomerPojo(3,"Ubaid","Hill park",96993);
		
		String data ="{\n"
				+ "    \"id\": 3,\n"
				+ "    \"phoneNumber\": 96993,\n"
				+ "    \"customerName\": \"Ubaid\",\n"
				+ "    \"customerAddress\": \"Hill park\"\n"
				+ "}";
		
		TestRestTemplate rest = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<CustomerPojo> request = new HttpEntity<CustomerPojo>(newCustomer,headers);
		
		ResponseEntity<String> response=rest.postForEntity("http://localhost:8080/addcustomer",request,String.class);	
		try {
			assertEquals(HttpStatus.CREATED,response.getStatusCode());
			JSONAssert.assertEquals(data,response.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	@Order(5)
	public void test_updatecustomer() {
		CustomerPojo newCustomer = new CustomerPojo(3,"Ubaid Alavi","Hill park",96993);
		
		String data ="{\n"
				+ "    \"id\": 3,\n"
				+ "    \"phoneNumber\": 96993,\n"
				+ "    \"customerName\": \"Ubaid Alavi\",\n"
				+ "    \"customerAddress\": \"Hill park\"\n"
				+ "}";
		
		TestRestTemplate rest = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<CustomerPojo> request = new HttpEntity<CustomerPojo>(newCustomer,headers);
		
		ResponseEntity<String> response=rest.exchange("http://localhost:8080/updatecustomer/3",HttpMethod.PUT,request,String.class);	
		try {
			assertEquals(HttpStatus.OK,response.getStatusCode());
			JSONAssert.assertEquals(data,response.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	@Order(6)
	public void test_deletecustomer() {
		CustomerPojo newCustomer = new CustomerPojo(3,"Ubaid Alavi","Hill park",96993);
		
		String data ="{\n"
				+ "    \"message\": \"Customer deleted successfully...\",\n"
				+ "    \"id\": 3\n"
				+ "}";
		
		TestRestTemplate rest = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<CustomerPojo> request = new HttpEntity<CustomerPojo>(newCustomer,headers);
		
		ResponseEntity<String> response=rest.exchange("http://localhost:8080/deletecustomer/3",HttpMethod.DELETE,request,String.class);	
		try {
			assertEquals(HttpStatus.OK,response.getStatusCode());
			JSONAssert.assertEquals(data,response.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
