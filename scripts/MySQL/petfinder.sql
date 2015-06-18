CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(60) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));
 
CREATE TABLE user_roles (
  user_role_id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  ROLE VARCHAR(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (ROLE,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));
 
INSERT INTO users(username,password,enabled)
VALUES ('ian','bob', TRUE);
INSERT INTO users(username,password,enabled)
VALUES ('batman','pizza', TRUE);
 
INSERT INTO user_roles (username, ROLE)
VALUES ('ian', 'ROLE_USER');
INSERT INTO user_roles (username, ROLE)
VALUES ('ian', 'ROLE_ADMIN');
INSERT INTO user_roles (username, ROLE)
VALUES ('batman', 'ROLE_USER');