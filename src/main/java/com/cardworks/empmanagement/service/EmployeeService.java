package com.cardworks.empmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardworks.empmanagement.dto.EmployeeDto;
import com.cardworks.empmanagement.model.Employee;
import com.cardworks.empmanagement.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

@Autowired
ModelMapper modelMapper;	
	
@Autowired	
EmployeeRepository empRepository;

public Employee createEmployee(Employee emp) {
	return empRepository.save(emp);
}

public List<EmployeeDto> getAllEmployee(){
	List<EmployeeDto> emDto = new ArrayList<>();
	List<Employee> list = empRepository.findAll();
	for(Employee e:list) {
		EmployeeDto em = modelMapper.map(e, EmployeeDto.class);
		emDto.add(em);
	}
	 return emDto;
}

public Employee findByEmail(String email) {
	return empRepository.findByEmail(email);
}

public Employee updateEmployee(int id, Employee emp) {
	
	Employee existingEmp = null;
	
	Optional<Employee> em = empRepository.findById(id);
	if(em.isPresent()) {
		existingEmp = em.get();
		existingEmp.setName(emp.getName());
		existingEmp.setEmail(emp.getEmail());
		existingEmp.setDepartment(emp.getDepartment());
		existingEmp.setSalary(emp.getSalary());
	}
	
	return empRepository.save(existingEmp);
}

public EmployeeDto findEmployeeById(int id) {
	EmployeeDto edto = modelMapper.map(empRepository.findById(id).get(), EmployeeDto.class);
	return edto;
}
}
