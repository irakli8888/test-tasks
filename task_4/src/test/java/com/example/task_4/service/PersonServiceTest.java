package com.example.task_4.service;

import com.example.task_4.model.Book;
import com.example.task_4.model.Person;
import com.example.task_4.repository.BookRepository;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PersonRepository personRepository;

    private static final Person person=mock(Person.class);

    private static final Book book=mock(Book.class);

    public static final Long id=33L;

    public static final ArrayList<Book> books=new ArrayList<>();

    private static final String firstName="person";

    @BeforeAll
    public static void setup(){
        books.add(book);
        when(person.getId()).thenReturn(id);
        when(person.getFirstName()).thenReturn(firstName);
        when(book.getId()).thenReturn(id);
    }

    @Test
    public void findAll(){
        ArrayList<Person> persons=new ArrayList<>();
        persons.add(person);
        when(personRepository.findAll()).thenReturn(persons);
        Assert.assertEquals(1L,persons.size());
    }

    @Test
    public void deleteBookForPerson(){
        when(personRepository.existsById(anyLong())).thenReturn(true);
        when(personRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(person));
        personService.deleteBookForPerson(person.getId(),book.getId());
        verify(personRepository).save(Mockito.any());
    }

    @Test
    public void getPersonsBook(){
        books.add(book);
        when(person.getBooks()).thenReturn(books);
        when(personRepository.existsById(anyLong())).thenReturn(true);
        when(personRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(person));
        Assert.assertTrue(personService.getPersonsBook(id).contains(book));
    }

    @Test
    public void add(){
        personRepository.save(person);
        bookRepository.saveAll(books);
        verify(personRepository).save(Mockito.any());
        verify(bookRepository).saveAll(Mockito.any());
    }

    @Test
    public void deleteById(){
        when(personRepository.existsById(anyLong())).thenReturn(true);
        personService.deleteById(id);
        verify(personRepository).deleteById(anyLong());
    }

    @Test
    public void deleteByName(){
        when(personRepository.existsByFirstNameAndLastNameAndMiddleName(anyString(),
                anyString(),anyString())).thenReturn(true);
        personService.deleteByName("aaa aa aa");
        verify(personRepository).deleteByFirstNameAndLastNameAndMiddleName(anyString(),
                anyString(),anyString());
    }
}
