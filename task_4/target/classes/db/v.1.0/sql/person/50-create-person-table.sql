
CREATE TABLE  person (
  id BIGINT NOT NULL AUTO_INCREMENT,
  post_time DATETIME(6) NULL,
  put_time DATETIME(6) NULL,
  version DOUBLE NULL,
  birth_date DATETIME(6) NOT NULL,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  middle_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB;



