
package com.example.task_4.service;


import com.example.task_4.model.Genre;
import com.example.task_4.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    @Transactional
    public List<Genre> findAll()
    {
        return (List<Genre>) genreRepository.findAll();
    }

    @Transactional
    public Genre addGenre(String name){
        Genre genre=new Genre();
        genre.setName(name);
        genreRepository.save(genre);
        return genre;
    }

    @Transactional
    public String booksCount(String name){
        if(genreRepository.existsByName(name)) {
            return "number of books in this genre: " + genreRepository.findByName(name).getBooks().stream().count();
        }
        return "there is no such genre yet";
    }
}

