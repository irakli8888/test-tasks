package com.example.task_4.controller;


import com.example.task_4.model.Genre;
import com.example.task_4.service.GenreService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    //выводим все жанры
    @GetMapping("/genre")
    @JsonView(GenreController.class)
    public List<Genre> getGenres(){
        return genreService.findAll();
    }

    //добавляем новый жанр без книг
    @PostMapping("/genre/add/{name}")
    public Genre addGenre(@PathVariable String name){
        return genreService.addGenre(name);
    }

    //выводим статитсику по жанру
    @GetMapping("/genre/books_count/{name}")
    public String booksCount(String name){
        return genreService.booksCount(name);
    }


}
