package com.cardworks.empmanagement.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="employee")
@EntityListeners(AuditingEntityListener.class)
public class Employee {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="department")
	private String department;
	
	@Column(name="salary")
	private double salary;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date",nullable=false,updatable=false)
	private Date createdDate;

	@ElementCollection(fetch=FetchType.LAZY)
	@CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
	@Column(name = "skills")
	private List<String> skills = new ArrayList<String>();
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	@JsonManagedReference
	private List<Address> address = new ArrayList();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Employee(int id, String name, String email, String department, double salary, List<String> skills,List<Address> address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.department = department;
		this.salary = salary;
		this.skills = skills;
		this.address=address;
	}

	public Employee(int id, String name, String email, String department, double salary, List<String> skills) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.department = department;
		this.salary = salary;
		this.skills = skills;
	}
	
	public Employee() {
		super();
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}
	
	public void addAddress(Address addr) {
	    address.add(addr);
	    addr.setEmployee(this);
	}	
	
}