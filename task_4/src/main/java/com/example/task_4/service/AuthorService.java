package com.example.task_4.service;

import com.example.task_4.model.Author;
import com.example.task_4.model.Book;
import com.example.task_4.repository.AuthorRepository;
import com.example.task_4.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Transactional
    public List<Author> findAll(){
        return (ArrayList<Author>) authorRepository.findAll();
    }

    @Transactional
    public List<Book> findAuthorsBooksById(Long id){
        return authorRepository.findById(id).get().getBooks();
    }

    @Transactional
    public void save(Author author){
        if(!author.getBooks().isEmpty()) {
            author.getBooks().forEach(book -> {
                book.setAuthor(author);
                bookRepository.save(book);
            });
        }
        authorRepository.save(author);
    }

    @Transactional
    public boolean existsById(Long id){
        return authorRepository.existsById(id);
    }

    @Transactional
    public Author findById(Long id){
        return authorRepository.findById(id).get();
    }

    @Transactional
    public void delete(Author author){
        authorRepository.delete(author);
    }


    @Transactional
    public ResponseEntity deleteByName( Long id) {
        if (existsById(id) && findById(id).getBooks().isEmpty()) {
            delete(findById(id));
            return new ResponseEntity(HttpStatus.OK);
        } else if (!findById(id).getBooks().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("you can't delete an author while they have books");
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);

    }
}
