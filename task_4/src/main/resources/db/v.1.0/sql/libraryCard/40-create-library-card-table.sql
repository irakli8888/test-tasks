CREATE TABLE library_card (
  book_id BIGINT NOT NULL,
  person_id BIGINT NOT NULL,
  post_time DATETIME(6) NULL,
  put_time DATETIME(6) NULL,
  version DOUBLE NULL,
  PRIMARY KEY (book_id, person_id),
  INDEX fk_book_has_person_person1_idx (person_id ASC) VISIBLE,
  INDEX fk_book_has_person_book1_idx (book_id ASC) VISIBLE,
  CONSTRAINT fk_book_has_person_book1
    FOREIGN KEY (book_id)
    REFERENCES book (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_book_has_person_person1
    FOREIGN KEY (person_id)
    REFERENCES person (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
