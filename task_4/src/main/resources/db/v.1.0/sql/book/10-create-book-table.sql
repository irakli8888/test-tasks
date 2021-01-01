CREATE TABLE book (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  author_id BIGINT ,
  date_of_receipt DATETIME(6) NULL,
  publication_date DATETIME(6) NULL,
  post_time DATETIME(6) NULL,
  put_time DATETIME(6) NULL,
  version DOUBLE NULL,
  PRIMARY KEY (id),
  INDEX fk_book_author_idx (author_id ASC) VISIBLE,
  CONSTRAINT fk_book_author
    FOREIGN KEY (author_id)
    REFERENCES author (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;