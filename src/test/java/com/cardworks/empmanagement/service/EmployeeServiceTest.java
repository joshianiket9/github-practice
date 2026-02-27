package com.cardworks.empmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.cardworks.empmanagement.model.Address;
import com.cardworks.empmanagement.model.Employee;
import com.cardworks.empmanagement.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@Mock
    private EmployeeRepository empRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private ModelMapper modelMapper;
    
    @Test
    void testCreateEmployee() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Aniket");

        when(empRepository.save(employee)).thenReturn(employee);

        // Act
        Employee savedEmployee = employeeService.createEmployee(employee);

        // Assert
        assertNotNull(savedEmployee);
        assertEquals(1L, savedEmployee.getId());
        assertEquals("Aniket", savedEmployee.getName());

        verify(empRepository, times(1)).save(employee);
    }

    @Test
    void testGetAllEmployee() {
    	
    	List<String> devSkills = List.of("Java","Springboot");
		List<String> testSkills = List.of("Testing","Load Runner");

		 Address addd = new Address(1L,"pune","mh","444333");
		 List<Address> listAdd = List.of(addd);
		
        // Arrange
		Employee emp1 = new Employee(1, "Aniket", "aniket@test.com","Developer",180000,devSkills,listAdd);
	    Employee emp2 = new Employee(2, "Rahul", "rahul@test.com","Tester",75000,testSkills,listAdd);

	    
        when(empRepository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        // Act
        List<Employee> employeeList = empRepository.findAll();

        // Assert
        assertNotNull(employeeList);
        assertEquals(2, employeeList.size());
        assertEquals("Aniket", employeeList.get(0).getName());

        verify(empRepository, times(1)).findAll();
    }
	
    @Test
    void testFindByEmail() {
  
    	String email = "aniket@testing.com";
    	List<String> devSkills = List.of("Java","Springboot");
    	Employee emp1 = new Employee(1, "Aniket", "aniket@test.com","Developer",180000,devSkills);
	    //Employee emp2 = new Employee(2, "Rahul", "rahul@test.com","Tester",75000,testSkills);
    	
    	when(empRepository.findByEmail(email)).thenReturn(emp1);
    	
    	Employee empActual = employeeService.findByEmail(email);
    	
    	assertNotNull(empActual);
    	assertEquals(empActual, emp1);
    }
}