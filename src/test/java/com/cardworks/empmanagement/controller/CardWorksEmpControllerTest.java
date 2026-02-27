package com.cardworks.empmanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cardworks.empmanagement.dto.AddressDto;
import com.cardworks.empmanagement.dto.EmployeeDto;
import com.cardworks.empmanagement.model.Employee;
import com.cardworks.empmanagement.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CardWorksEmpController.class)
public class CardWorksEmpControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService empService;

    @MockitoBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;
	
	@Test
	void createEmployee_shouldReturnCreatedEmployee() throws Exception {
	
		 EmployeeDto dto = new EmployeeDto();
	     Employee mappedEmployee = new Employee();
		
		dto.setId(1);
	    dto.setName("Aniket");
	    dto.setEmail("aniket@test.com");

	    mappedEmployee.setId(1);
	    mappedEmployee.setName("Aniket");
	    mappedEmployee.setEmail("aniket@test.com");
		
		when(modelMapper.map(any(EmployeeDto.class), eq(Employee.class)))
	            .thenReturn(mappedEmployee);

	    when(empService.createEmployee(any(Employee.class)))
	            .thenReturn(mappedEmployee);

	    // When & Then
	    mockMvc.perform(post("/createEmployee")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(dto)))
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$.id").value(1))
	            .andExpect(jsonPath("$.name").value("Aniket"))
	            .andExpect(jsonPath("$.email").value("aniket@test.com"));

	    verify(empService, times(1)).createEmployee(any(Employee.class));
	}	
	
	@Test
	void createEmployee_shouldReturnBadRequest() throws JsonProcessingException, Exception {
		
		EmployeeDto dto = new EmployeeDto();
	     Employee mappedEmployee = new Employee();
		
		dto.setId(1);
	    dto.setName("");
	    dto.setEmail("aniket@test.com");

	    mappedEmployee.setId(1);
	    mappedEmployee.setName("");
	    mappedEmployee.setEmail("aniket@test.com");
		
		 mockMvc.perform(post("/createEmployee")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(objectMapper.writeValueAsString(dto)))
		            .andExpect(status().isBadRequest());

		    verify(empService, never()).createEmployee(any());
		
	}
	
	@Test
	void getAllEmployees_shouldReturnEmployeeList() throws Exception {

		List<String> devSkills = List.of("Java","Springboot");
		List<String> testSkills = List.of("Testing","Load Runner");
		
		AddressDto adto1 = new AddressDto(1,"pune","mh","444333");
		List<AddressDto> listAdd = List.of(adto1);
		
	    EmployeeDto emp1 = new EmployeeDto(1, "Aniket", "aniket@test.com","Developer",180000,devSkills,listAdd);
	    EmployeeDto emp2 = new EmployeeDto(2, "Rahul", "rahul@test.com","Tester",75000,testSkills,listAdd);

	    when(empService.getAllEmployee())
	            .thenReturn(List.of(emp1, emp2));

	    
	    
	    mockMvc.perform(MockMvcRequestBuilders.get("/getAllEmployees"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.size()").value(2))
	            .andExpect(jsonPath("$[0].name").value("Aniket"))
	            .andExpect(jsonPath("$[1].name").value("Rahul"))
	    		.andExpect(jsonPath("$[0].email").value("aniket@test.com"))
	    		.andExpect(jsonPath("$[1].email").value("rahul@test.com"));
	    verify(empService, times(1)).getAllEmployee();
	}
	
	@Test
	void getAllEmployees_shouldReturnEmptyList() throws Exception {

	    when(empService.getAllEmployee()).thenReturn(Collections.emptyList());

	    mockMvc.perform(MockMvcRequestBuilders.get("/getAllEmployees"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.size()").value(0));
	}
	
	@Test
	void findEmployee_shouldReturnEmployeeByEmail() throws Exception {
		
		String email = "aniket@testing.com";
		
		Employee emp = new Employee();
		emp.setId(1);
		emp.setName("Aniket");
		emp.setEmail(email);

		when(empService.findByEmail(email)).thenReturn(emp);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/findEmployee/{email}",email)
				     .contentType(MediaType.APPLICATION_JSON))
					 .andExpect(status().isOk())
					 .andExpect(jsonPath("$.name").value("Aniket"))
					 .andExpect(jsonPath("$.email").value("aniket@testing.com"));
		
		verify(empService,times(1)).findByEmail(email);
	}
}