package com.example.task_4.service;

import com.example.task_4.model.Book;
import com.example.task_4.model.Employee;
import com.example.task_4.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public List<Employee> findAll(){
        return (List<Employee>) employeeRepository.findAll();
    }

    @Transactional
    public List<Employee> add(Employee employee) {
        employeeRepository.save(employee);
        return findAll();
    }

    @Transactional
    public ResponseEntity deleteById(Long id) {
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);//если нет, то NOT_MODIFIED
    }
}
