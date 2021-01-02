package com.example.task_4.service;

import com.example.task_4.model.Author;
import com.example.task_4.model.Book;
import com.example.task_4.model.Genre;
import com.example.task_4.repository.AuthorRepository;
import com.example.task_4.repository.BookRepository;
import com.example.task_4.repository.GenreRepository;
import com.example.task_4.repository.PersonRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AuthorRepository authorRepository;

    private static final Book book = mock(Book.class);

    public static final Long id = 33L;

    public static final ArrayList<Book> books = new ArrayList<>();

    private static final String name = "book";

    private static final Author author = mock(Author.class);

    private static final Genre genre = mock(Genre.class);

    public static final ArrayList<Genre> genres = new ArrayList<>();


    @BeforeAll
    public static void setup() {
        when(book.getId()).thenReturn(id);
        when(book.getName()).thenReturn(name);
        author.setId(22L);
        author.setFirstName("ss");
        when(book.getAuthor()).thenReturn(author);
    }

    @Test
    public void findAll() {
        books.add(book);
        when(bookRepository.findAll()).thenReturn(books);
        Assert.assertEquals(1L, bookService.findAll().size());
    }

    @Test
    public void getBooksByGenre() {
        books.add(book);
        when(bookRepository.existsByGenresName(anyString())).thenReturn(true);
        when(bookRepository.findByGenresName(anyString())).thenReturn(books);
        Assert.assertEquals(3L, bookRepository.findByGenresName("ss").size());
    }

    @Test
    public void add() {
        bookService.add(book);
        verify(bookRepository).save(Mockito.any());
    }

    @Test
    public void updateBookForGenre() {
        when(genreRepository.save(genre));
        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(book));
        when(book.getGenres()).thenReturn(genres);
        bookService.updateBookForGenre(book);
        verify(bookRepository).save(Mockito.any());
    }

    @Test
    public void deleteById() {
        when(bookRepository.existsById(anyLong())).thenReturn(true);
        bookService.deleteById(anyLong());
        verify(bookRepository).deleteById(anyLong());
    }
}
