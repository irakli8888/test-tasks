package com.example.task_4.controller;

import com.example.task_4.model.Author;
import com.example.task_4.model.Book;
import com.example.task_4.service.AuthorService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    //выводим авторов
    @JsonView(AuthorController.class)
    @GetMapping("/author")
    public List<Author> getAuthors(){
        return authorService.findAll();
    }

    //вывод книг жанра(указываем по id)
    @JsonView(AuthorController.class)
    @GetMapping("/author/{authorId}/books")
    public List<Book> getAuthorsBooks(@PathVariable Long authorId){
        return authorService.findAuthorsBooksById(authorId);
    }

    //сохраняем автора
    @PostMapping("/author/add")
    @JsonView(AuthorController.class)
    public Author addAuthor(@RequestBody Author author) {
        authorService.save(author);
        return author;
    }

    // удаляем по id, если нет книг
    @DeleteMapping("/author/delete_by_id/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        return authorService.deleteByName(id);
    }
}
