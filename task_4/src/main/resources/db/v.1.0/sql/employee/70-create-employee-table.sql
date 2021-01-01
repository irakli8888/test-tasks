CREATE table employee (
  id BIGINT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(45) NOT NULL,
  middle_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  post_time DATETIME(6) NULL,
  put_time DATETIME(6) NULL,
  version DOUBLE NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB;