package com.example.task_4.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


//добавляем последнюю сущность, чтобы liquibase валидировался правильно
@Entity
@Table(name ="book_genre_ink")
@IdClass(BookGenreInkKey.class)
@Data
public class BookGenreInk extends BaseEntity {

    @Id
    @Column(name="book_id")
    private Long bookId;

    @Id
    @Column(name="genre_id")
    private Long genreId;

}

@Getter
@Setter
class BookGenreInkKey implements Serializable {
    private Long bookId;
    private Long genreId;
}