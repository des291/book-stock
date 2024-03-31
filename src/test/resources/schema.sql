DROP TABLE IF EXISTS Books;

CREATE TABLE IF NOT EXISTS Books (
    id INT NOT NULL AUTO_INCREMENT,
    title varchar(250) NOT NULL,
    author varchar(250) NOT NULL,
    published_year INT NOT NULL,
    genre varchar(250) NOT NULL,
    PRIMARY KEY (id)
);