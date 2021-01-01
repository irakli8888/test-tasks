CREATE TABLE book_genre_ink (
  book_id BIGINT NOT NULL,
  genre_id BIGINT NOT NULL,
  post_time DATETIME(6) NULL,
  put_time DATETIME(6) NULL,
  version DOUBLE NULL,
  PRIMARY KEY (book_id, genre_id),
  INDEX fk_book_has_genre_genre1_idx (genre_id ASC) VISIBLE,
  INDEX fk_book_has_genre_book1_idx (book_id ASC) VISIBLE,
  CONSTRAINT fk_book_has_genre_book1
    FOREIGN KEY (book_id)
    REFERENCES book (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_book_has_genre_genre1
    FOREIGN KEY (genre_id)
    REFERENCES genre (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
