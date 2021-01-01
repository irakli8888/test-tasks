package com.example.task_4.controller;

import com.example.task_4.model.Book;

import com.example.task_4.service.BookService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    //вывовдим все книги
    @GetMapping("/book")
    @JsonView(BookController.class)
    public List<Book> getBooks() {
        return bookService.findAll();
    }


    // выводим книги по жанру
    @GetMapping("/book/get_by_genre/{genre}")
    @JsonView(BookController.class)
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }

    //выводим книги по автору(любой комбинацие фио)
    @GetMapping("/book/get_by_author/{author}")
    @JsonView(BookController.class)
    public String getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    //создаем новую книгу
    @JsonView(BookController.class)
    @PostMapping("/book/add")
    public Book add(@RequestBody Book book) {
        bookService.add(book);
        return book;
    }

    //выписываем новый жанр в тело books
    @JsonView(BookController.class)
    @PutMapping("/book/put")
    public  List<Book> updateBookForGenre(@RequestBody Book book ){
        return  bookService.updateBookForGenre(book);

    }

    //удаляем по id, если нет пользователя
    @DeleteMapping("/book/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        return bookService.deleteById(id);
    }
}
