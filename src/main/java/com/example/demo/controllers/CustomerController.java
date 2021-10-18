package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.beans.AddResponse;
import com.example.demo.beans.CustomerPojo;
import com.example.demo.services.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	CustomerService service;

	@GetMapping("/getcustomers")
	public List<CustomerPojo> NavigateToHome() {
		return service.Home();
	}

	@GetMapping("/getcustomer/{id}")
	public ResponseEntity<CustomerPojo> getCustomerById(@PathVariable(value = "id") int id) {
		try {
			CustomerPojo cus = service.getCustomerById(id);
			return new ResponseEntity<CustomerPojo>(cus, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getcustomerbyname/{name}")
	public ResponseEntity<CustomerPojo> getCustomerByName(@PathVariable(value = "name") String name) {
		try {
			CustomerPojo cus = service.getCustomerByName(name);
			return new ResponseEntity<CustomerPojo>(cus, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/addcustomer")
	public CustomerPojo addCustomer(@RequestBody CustomerPojo b) {
		return service.addCustomer(b);
	}

	@DeleteMapping("/deletecustomer/{id}")
	public AddResponse deleteCustomer(@PathVariable(value = "id") int id) {
		return service.deleteCustomer(id);

	}

	@PutMapping("/updatecustomer/{id}")
	public ResponseEntity<CustomerPojo> updateCustomer(@PathVariable(value = "id") int id,
			@RequestBody CustomerPojo customer) {

		try {
			CustomerPojo obj = service.getCustomerById(id);
			obj.setCustomerName(customer.getCustomerName());
			obj.setCustomerAddress(customer.getCustomerAddress());
			obj.setPhoneNumber(customer.getPhoneNumber());

			CustomerPojo updated_customer = service.updateCustomer(obj);

			return new ResponseEntity<CustomerPojo>(updated_customer, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

}
