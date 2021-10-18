package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.beans.CustomerPojo;

public interface CustomerRepository extends JpaRepository<CustomerPojo,Integer> {

}
