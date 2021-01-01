package com.example.task_4.controller;


import com.example.task_4.model.Employee;
import com.example.task_4.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employee")
    public List<Employee> getBooks() {
        return employeeService.findAll();
    }

    @PostMapping("/employee")
    public Employee add(@RequestBody Employee employee) {
        employeeService.add(employee);
        return employee;
    }

    @PutMapping("/employee")
    public  List<Employee> update(@RequestBody Employee employee ){
        return  employeeService.add(employee);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        return employeeService.deleteById(id);
    }

}
