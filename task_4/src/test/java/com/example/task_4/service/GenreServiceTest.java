package com.example.task_4.service;

import com.example.task_4.model.Book;
import com.example.task_4.model.Genre;
import com.example.task_4.repository.GenreRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreServiceTest {

    @InjectMocks
    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;

    private static final Genre genre=mock(Genre.class);

    private static final Book book=mock(Book.class);

    public static final ArrayList<Genre> genres=new ArrayList<>();

    public static final ArrayList<Book> books=new ArrayList<>();

    private static final String name="genre";

    public static final Long id=33L;

    @BeforeAll
    public static void setup(){
        when(genre.getId()).thenReturn(id);
        when(genre.getName()).thenReturn(name);
    }

    @Test
    public void findAll(){
        genres.add(genre);
        when(genreRepository.findAll()).thenReturn(genres);
        Assert.assertEquals(1L,genreService.findAll().size());
    }

    @Test
    public void addGenre(){
        genreService.addGenre(anyString());
        verify(genreRepository).save(any());
    }

    @Test
    public void bookCount(){
        books.add(book);
        when(genreRepository.existsByName(anyString())).thenReturn(true);
        when(genreRepository.findByName(anyString())).thenReturn(genre);
        when(genre.getBooks()).thenReturn(books);
        String value=genreService.booksCount(anyString());
        Assert.assertEquals("number of books in this genre: 1",value);
    }

}
