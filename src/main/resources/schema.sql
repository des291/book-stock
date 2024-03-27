CREATE TABLE IF NOT EXISTS Books (
    id INT NOT NULL,
    title varchar(250) NOT NULL,
    author varchar(250) NOT NULL,
    pub_year INT NOT NULL,
    genre varchar(250) NOT NULL,
    PRIMARY KEY (id)
);