package com.example.task_4.model;

import com.example.task_4.controller.BookController;
import com.example.task_4.controller.PersonsController;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Person extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({BookController.class, PersonsController.class})
    private Long id;

    @Column(name="birth_date")
    @JsonView({BookController.class,PersonsController.class})
    private ZonedDateTime birthDate;

    @JsonView({BookController.class,PersonsController.class})
    private String firstName;

    @JsonView({BookController.class,PersonsController.class})
    private String lastName;

    @JsonView({BookController.class,PersonsController.class})
    private String middleName;

    @OneToOne(mappedBy = "person")
    private User user;

    @JsonView(PersonsController.class)
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(name = "library_card",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private List<Book> books;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                '}';
    }
}
