package com.cardworks.empmanagement.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cardworks.empmanagement.dto.EmployeeDto;
import com.cardworks.empmanagement.model.Employee;
import com.cardworks.empmanagement.service.EmployeeService;    

import jakarta.validation.Valid;

@RestController
public class CardWorksEmpController {

	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/createEmployee")
	public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto emp){
		
		com.cardworks.empmanagement.model.Employee employee = modelMapper.map(emp, com.cardworks.empmanagement.model.Employee.class);
		if(employee.getAddress()!=null) {
			employee.getAddress().forEach(ad ->ad.setEmployee(employee));
		}
		com.cardworks.empmanagement.model.Employee empp = empService.createEmployee(employee);
		 modelMapper.map(empp, emp);
		 return ResponseEntity.status(HttpStatus.CREATED).body(emp);
	}
	
	@GetMapping("/getAllEmployees")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
		List<EmployeeDto> list = empService.getAllEmployee();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@GetMapping("/findEmployee/{email}")
	public ResponseEntity<com.cardworks.empmanagement.model.Employee> findEmployee(@PathVariable("email") String email){
		Employee employee = empService.findByEmail(email);
		return ResponseEntity.status(HttpStatus.OK).body(employee);
	}
	
	@PutMapping("/updateEmployee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int id, @Valid @RequestBody EmployeeDto empDto){
		Employee employee = modelMapper.map(empDto,Employee.class);
		Employee updatedEmployee = empService.updateEmployee(id, employee);
		//EmployeeDto employeeDto = modelMapper.map(updatedEmployee, EmployeeDto.class);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@GetMapping("/findEmployeeById/{id}")
	public ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable("id") int id){
		empService.findEmployeeById(id);
		EmployeeDto emp = empService.findEmployeeById(id);
		return ResponseEntity.status(HttpStatus.OK).body(emp);
	}
	
}