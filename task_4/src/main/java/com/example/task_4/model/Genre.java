package com.example.task_4.model;

import com.example.task_4.controller.AuthorController;
import com.example.task_4.controller.BookController;
import com.example.task_4.controller.GenreController;
import com.example.task_4.controller.PersonsController;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;


import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class Genre extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @JsonView({BookController.class, PersonsController.class, AuthorController.class, GenreController.class})
    private String name;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "book_genre_ink",
            joinColumns = {@JoinColumn(name = "genre_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private List<Book> books;

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
