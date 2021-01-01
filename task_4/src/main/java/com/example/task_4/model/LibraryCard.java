package com.example.task_4.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "library_card")
@IdClass(LibraryCardKey.class)
@Data
public class LibraryCard extends BaseEntity {
    public LibraryCard() {
        this.dateOfReturnOfBooks =ZonedDateTime.now().plusDays(7);
    }

    @Id
    @Column(name="book_id")
    private Long bookId;

    @Id
    @Column(name="person_id")
    private Long personId;

    private ZonedDateTime dateOfReturnOfBooks;

}

@Getter
@Setter
class LibraryCardKey implements Serializable{
    private Long bookId;
    private Long personId;
}
