package com.example.task_4.controller;


import com.example.task_4.model.Person;
import com.example.task_4.service.LibraryCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LibraryCardController {

    private final LibraryCardService libraryCardService;

    @GetMapping("/library_card")
    public List<Person> getAllDebtor(){
        return libraryCardService.getAllDebtor();
    }

    @PutMapping("/library_card/add_days/{personId}/{bookId}/{days}")
    public void addDays(@PathVariable Long personId,@PathVariable Long bookId,@PathVariable Integer days){
        libraryCardService.addDays(personId,bookId,days);
    }


}
