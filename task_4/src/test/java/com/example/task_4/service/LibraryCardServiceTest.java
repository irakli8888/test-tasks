package com.example.task_4.service;

import com.example.task_4.model.LibraryCard;
import com.example.task_4.model.Person;
import com.example.task_4.repository.LibraryCardRepository;
import com.example.task_4.repository.PersonRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryCardServiceTest {

    @InjectMocks
    private LibraryCardService libraryCardService;

    @Mock
    private LibraryCardRepository libraryCardRepository;

    @Mock
    private PersonRepository personRepository;

    private static final Person person=mock(Person.class);

    private static final LibraryCard libraryCard=mock(LibraryCard.class);

    public static final ArrayList<Person> persons=new ArrayList<>();

    public static final ArrayList<LibraryCard> libraryCards=new ArrayList<>();

    @Test
    public void getAllDebtor(){
        libraryCards.add(libraryCard);
        when(libraryCard.getDateOfReturnOfBooks()).thenReturn(ZonedDateTime.of(2016,3,5,7,7
                ,20,23, ZoneId.of("Europe/Moscow")));
        when(personRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(person));
        when(libraryCardRepository.findAll()).thenReturn(libraryCards);
        Assert.assertEquals(2,libraryCardService.getAllDebtor().size());
    }

    @Test
    public void addDays(){
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        libraryCards.add(libraryCard);
        when(libraryCardRepository.findAll()).thenReturn(libraryCards);
        when(libraryCard.getBookId()).thenReturn(1L);
        when(libraryCard.getPersonId()).thenReturn(1L);
        when(libraryCard.getDateOfReturnOfBooks()).thenReturn(zonedDateTime);
        libraryCardService.addDays(1L,1L,3);
        verify(libraryCardRepository).saveAll(libraryCards);
    }
}
