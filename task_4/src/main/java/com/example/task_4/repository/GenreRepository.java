package com.example.task_4.repository;

import com.example.task_4.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends CrudRepository<Genre,Long> {
    List<Genre> findAllByName(String name);
    Genre findByName(String name);
    boolean existsByName(String name);
}
