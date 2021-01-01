package com.example.task_4.service;


import com.example.task_4.model.Book;
import com.example.task_4.model.Person;
import com.example.task_4.repository.BookRepository;
import com.example.task_4.repository.GenreRepository;
import com.example.task_4.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final LibraryCardService libraryCardService;

    @Transactional
    public List<Person> findAll()
    {
        return (List<Person>) personRepository.findAll();
    }

    @Transactional
    public Person addBookForPerson(Long personId, Book book){
        if(libraryCardService.getAllDebtor().contains(personRepository.existsById(personId))) {//проевряем должника
            System.out.println("You can't get a new book until you get the old one back");
            return null;
        }
        if(personRepository.existsById(personId)) {
            Optional<Person> person = personRepository.findById(personId);
            person.get().getBooks().add(book);
            person.get().getBooks().forEach(book1 -> genreRepository.saveAll(book1.getGenres()));
            personRepository.save(person.get());
            bookRepository.saveAll(person.get().getBooks());
            return person.get();
        }
        return null;
    }

    @Transactional
    public ResponseEntity deleteBookForPerson( Long personId, Long bookId){
        if(personRepository.existsById(personId)) {
            Optional<Person> person = personRepository.findById(personId);
            Optional<Book> book = bookRepository.findById(bookId);
            List<Book> books = person.get().getBooks().stream().filter(a -> a.getId() != bookId).collect(Collectors.toList());
            person.get().setBooks(books);
            personRepository.save(person.get());
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("this user is missing");//если нет, то NOT_MODIFIED

    }

    @Transactional
    public List<Book> getPersonsBook(Long id){
        if(personRepository.existsById(id)) {
            Optional<Person> person = personRepository.findById(id);
            return person.get().getBooks();
        }
        return null;
    }

    @Transactional
    public Person add(Person person) {
        bookRepository.saveAll(person.getBooks());
        personRepository.save(person);
        return person;
    }

    @Transactional
    public ResponseEntity deleteById( Long id) {
        if(personRepository.existsById(id)){
            personRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
    }
    @Transactional
    public ResponseEntity deleteByName(String name) {
        String pice[]=name.trim().split(" ");
        if(personRepository.existsByFirstNameAndLastNameAndMiddleName(pice[0],pice[1],pice[2])){
            personRepository.deleteByFirstNameAndLastNameAndMiddleName(pice[0],pice[1],pice[2]);
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("this user is missing");//если нет, то NOT_MODIFIED
    }
}