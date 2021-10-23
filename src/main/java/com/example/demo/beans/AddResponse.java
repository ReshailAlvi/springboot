package com.example.demo.beans;

public class AddResponse {

	String message;
	int id;
	
    public AddResponse(int id,String m) {
	this.id = id;
	this.message = m;
	}
   
    public AddResponse(){}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
