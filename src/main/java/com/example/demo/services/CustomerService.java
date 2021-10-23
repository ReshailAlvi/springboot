package com.example.demo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.beans.AddResponse;
import com.example.demo.beans.CustomerPojo;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository repo;

	public List<CustomerPojo> getallcustomers() {

		return repo.findAll();

	}

	public CustomerPojo getCustomerById(int id) {
		return repo.findById(id).get();
	}

	public CustomerPojo addCustomer(CustomerPojo b) {
		b.setId(getSize());
		repo.save(b);
		return b;
	}

	public int getSize() {
		return repo.findAll().size() + 1;
	}

	public AddResponse deleteCustomer(int id) {
		repo.deleteById(id);
		AddResponse res = new AddResponse();

		res.setId(id);
		res.setMessage("Customer deleted successfully...");

		return res;
	}

	public CustomerPojo getCustomerByName(String name) {
		List<CustomerPojo> customers = repo.findAll();
		CustomerPojo cus = null;
		for (CustomerPojo i : customers) {
			if (i.getCustomerName().equalsIgnoreCase(name)) {
				cus = i;
			}
		}

		return cus;
	}

	public CustomerPojo updateCustomer(CustomerPojo b) {
		repo.save(b);
		return b;
	}

}
