package com.example.task_4.repository;

import com.example.task_4.model.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PersonRepository extends CrudRepository<Person,Long> {

    @Modifying
    @Transactional
    void deleteByFirstNameAndLastNameAndMiddleName(String firstName, String lastName, String middleName);


    boolean existsByFirstNameAndLastNameAndMiddleName(String firstName, String lastName,String middleName);

}
