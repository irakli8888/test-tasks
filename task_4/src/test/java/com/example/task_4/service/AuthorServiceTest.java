package com.example.task_4.service;

import com.example.task_4.model.Author;
import com.example.task_4.model.Book;
import com.example.task_4.repository.AuthorRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    private static final Author author=mock(Author.class);

    private static final Book book=mock(Book.class);

    public static final Long id=1L;

    private static final String firstName="author";

    @BeforeAll
    public static void setup(){
        when(author.getId()).thenReturn(id);
        when(author.getFirstName()).thenReturn(firstName);
        when(book.getId()).thenReturn(id);
    }

    @Test
    public void findAll(){
        ArrayList<Author> authors=new ArrayList<>();
        authors.add(author);
        when(authorRepository.findAll()).thenReturn(authors);
        Assert.assertEquals(1L,authors.size());
    }

    @Test
    public void save(){
        authorRepository.save(author);
        verify(authorRepository).save(Mockito.any());
    }

    @Test
    public void existById(){
        when(authorRepository.existsById(anyLong())).thenReturn(true);
        Assert.assertTrue(authorService.existsById(1L));
    }

    @Test
    public void findById(){
        ArrayList <Author> authors=new ArrayList<>();
        authors.add(author);
        given(authorRepository.findById(1L)).willReturn(
                java.util.Optional.of(author));
        Assert.assertEquals(author,authorService.findById(1L));
    }

    @Test
    public void findAuthorsBooksById(){
        ArrayList<Book> books=new ArrayList<>();
        books.add(book);
        when(authorRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(author));
        when(author.getBooks()).thenReturn(books);
        Assert.assertTrue(authorService.findAuthorsBooksById(id).equals(books));
    }

    @Test
    public void delete(){
        authorService.delete(author);
        verify(authorRepository).delete(Mockito.any());
    }

}
