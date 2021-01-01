package com.example.task_4.controller;

import com.example.task_4.model.Book;
import com.example.task_4.model.Person;


import com.example.task_4.service.PersonService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequiredArgsConstructor
public class PersonsController {

    private final PersonService personService;
    //выводим пользователей
    @JsonView(PersonsController.class)
    @GetMapping("/person")
    public List<Person> getPersons(){
        return personService.findAll();
    }

    //добавляем книгу пользователю
    @JsonView(PersonsController.class)
    @PutMapping("/person/add_books/{personId}")
    public Person addBookForPerson(@PathVariable Long personId,@RequestBody Book book){
        return personService.addBookForPerson(personId,book);
    }

    //удаляем книгу пользователя
    @JsonView(PersonsController.class)
    @PutMapping("/person/delete_books/{personId}/{bookId}")
    public ResponseEntity deleteBookForPerson(@PathVariable Long personId,@PathVariable Long bookId){
        return personService.deleteBookForPerson(personId,bookId);
    }

    //выводим книги пользователя
    @JsonView(PersonsController.class)
    @GetMapping("/person/books/{id}")
    public List<Book> getPersonsBook(@PathVariable Long id){
        return personService.getPersonsBook(id);
    }

    //добавляем нового пользователя
    @PostMapping("/person/add")
    public Person add(@RequestBody Person person) {
        return personService.add(person);
    }

    //удаляем по id
    @DeleteMapping("/person/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        return personService.deleteById(id);
    }

    //удаляем по фио
    @DeleteMapping("/person/delete_by_name/{name}")
    public ResponseEntity deleteByName(@PathVariable String name) {
        return personService.deleteByName(name);
    }
}
