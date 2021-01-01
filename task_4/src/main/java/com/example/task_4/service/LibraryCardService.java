package com.example.task_4.service;

import com.example.task_4.model.LibraryCard;
import com.example.task_4.model.Person;
import com.example.task_4.repository.LibraryCardRepository;
import com.example.task_4.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryCardService {

    private final LibraryCardRepository libraryCardRepository;
    private final PersonRepository personRepository;

    //ищем просроченные на данный момент карты и возвращаем пользователей,которые нашкодили
    @Transactional
    public List<Person> getAllDebtor(){
        List<LibraryCard>cards= (List<LibraryCard>) libraryCardRepository.findAll();
        cards.stream().filter(a->a.getDateOfReturnOfBooks().isBefore(ZonedDateTime.now())).collect(Collectors.toList());
        return cards.stream().map(c->personRepository.findById(c.getPersonId()).get()).collect(Collectors.toList());
    }

    //на входе id книги, пользователя,число плюсуемых дней
    //фильтруем и получем нужную карту,потом добавляем дни, потом отправляем в бд
    @Transactional
    public void addDays(Long personId,  Long bookId, Integer days){
        List<LibraryCard>cards= (List<LibraryCard>) libraryCardRepository.findAll();
        cards.stream().filter(a->a.getBookId().equals(bookId)&&a.getPersonId().equals(personId))
                .map(e->e.getDateOfReturnOfBooks().plusDays(days)).collect(Collectors.toList());
        libraryCardRepository.saveAll(cards);
    }

}
