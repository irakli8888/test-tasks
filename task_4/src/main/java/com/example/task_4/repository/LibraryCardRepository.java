package com.example.task_4.repository;

import com.example.task_4.model.LibraryCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryCardRepository extends CrudRepository<LibraryCard, Long> {
}
