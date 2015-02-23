-- Schema creation
USE information_schema;

DROP DATABASE IF EXISTS ofsportal;

CREATE DATABASE ofsportal;

-- Table creation
USE ofsportal;

CREATE TABLE role (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(100) NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX name_UNIQUE (name ASC));

CREATE TABLE widgets (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NULL,
  description VARCHAR(100) NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX name_UNIQUE (name ASC));
  
CREATE TABLE widgets_role (
  widget_id INT NOT NULL,
  role_id INT NOT NULL,
  INDEX id_idx (widget_id ASC),
  INDEX role_fk_idx (role_id ASC),
  CONSTRAINT widget_fk
    FOREIGN KEY (widget_id)
    REFERENCES widgets (id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT role_fk
    FOREIGN KEY (role_id)
    REFERENCES role (id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

