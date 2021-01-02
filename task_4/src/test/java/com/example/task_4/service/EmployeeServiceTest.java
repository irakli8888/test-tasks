package com.example.task_4.service;

import com.example.task_4.model.Employee;
import com.example.task_4.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    private static final Employee employee=mock(Employee.class);

    public static final ArrayList<Employee> employees=new ArrayList<>();

    private static final String name="ivan";

    public static final Long id=33L;

    @BeforeAll
    public static void setup(){
        when(employee.getId()).thenReturn(id);
        when(employee.getFirstName()).thenReturn(name);
    }

    @Test
    public void findAll(){
        employees.add(employee);
        when(employeeRepository.findAll()).thenReturn(employees);
        Assert.assertEquals(1L,employeeService.findAll().size());
    }

    @Test
    public void add(){
        employeeService.add(any());
        verify(employeeRepository).save(any());
    }

    @Test
    public void deleteById(){
        when(employeeRepository.existsById(anyLong())).thenReturn(true);
        employeeService.deleteById(anyLong());
        verify(employeeRepository).deleteById(anyLong());
    }

}
