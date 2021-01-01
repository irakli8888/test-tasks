package com.example.task_4.service;

import com.example.task_4.csv.CSVHelper;
import com.example.task_4.model.Employee;
import com.example.task_4.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {

    @Autowired
    EmployeeRepository employeeRepository;

    public void save(MultipartFile file) {
        try {
            List<Employee> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
            employeeRepository.saveAll(tutorials);
        } catch ( IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<Employee> getAllTutorials() {
        return (List<Employee>) employeeRepository.findAll();
    }

}
