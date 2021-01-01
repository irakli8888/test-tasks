package com.example.task_4.repository;


import com.example.task_4.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends CrudRepository<Book,Long> {

    boolean existsByGenresName(String name);
    List<Book> findByGenresName(String name);
    boolean existsByAuthorFirstName(String name);
    boolean existsByAuthorLastName(String name);
    boolean existsByAuthorMiddleName(String name);
}
