package com.cardworks.empmanagement.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {
    
	private int id;
	
	@NotBlank(message = "Name is required")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
	private String name;
	private String email;
	private String department;
	private int salary;
	private List<String> skills;
	private List<AddressDto> address;
	public EmployeeDto(int id, String name,String email, String department, int salary, List<String> skills, List<AddressDto> address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.department = department;
		this.salary = salary;
		this.skills = skills;
		this.address = address;
	}
	
	
	
}