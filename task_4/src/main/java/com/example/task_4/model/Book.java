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
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_of_receipt")
    private ZonedDateTime dateOfReceipt;

    @Column(name = "publication_date")
    private ZonedDateTime publicationDate;


    @JsonView({BookController.class, PersonsController.class, AuthorController.class})
    private String name;

    @JsonView({BookController.class,PersonsController.class})
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="author_id")
    private Author author;

    //Книги не должны быть удалены, просто удалён 1 из жанров
    @JsonView({BookController.class,PersonsController.class,AuthorController.class})
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(name = "book_genre_ink",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private List<Genre> genres;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "library_card",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "person_id")})
    private List<Person> persons;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author.getFirstName()+" "+author.getMiddleName()+ " "+author.getLastName()+
                '}'+
                " genre" + genres.toString()+'\'';
    }
}
