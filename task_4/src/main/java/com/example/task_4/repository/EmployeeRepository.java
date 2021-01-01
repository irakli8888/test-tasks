package com.example.task_4.repository;


import com.example.task_4.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository  extends CrudRepository<Employee,Long> {
}
