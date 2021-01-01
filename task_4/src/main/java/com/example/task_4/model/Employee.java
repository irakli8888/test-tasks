package com.example.task_4.model;

import com.example.task_4.controller.AuthorController;
import com.example.task_4.controller.BookController;
import com.example.task_4.controller.PersonsController;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;


@Entity
@Table
@Data
public class Employee extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private String firstName;

    private String lastName;

    private String middleName;

}
