package com.example.task_4.model;

import com.example.task_4.controller.AuthorController;
import com.example.task_4.controller.BookController;
import com.example.task_4.controller.PersonsController;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table
@Data
public class Author extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Column(name = "birth_date")
    private ZonedDateTime birthDate;

    @JsonView({BookController.class, PersonsController.class, AuthorController.class})
    private String firstName;

    @JsonView({BookController.class,PersonsController.class, AuthorController.class})
    private String lastName;

    @JsonView({BookController.class,PersonsController.class, AuthorController.class})
    private String middleName;
    //удаляется автор и его книги. Если какая-то книга есть у пользователя,
    // то возвращать ошибку «нельзя удалить автора и его книги, потому что 1 или несколько у пользователя
    @JsonView(AuthorController.class)
    @OneToMany(cascade = CascadeType.REMOVE,mappedBy = "author")
    private List<Book> books;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", book=" + books.toString() +
                '}';
    }
}
