CREATE DATABASE biblioteca;
USE biblioteca;

CREATE TABLE livros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    ano_publicacao INT
);
INSERT INTO livros (titulo, autor, ano_publicacao) VALUES ('Biblia', 'Deus', 1000);
INSERT INTO livros (titulo, autor, ano_publicacao) VALUES ('Caderno', 'Gabriel', 2023);

SELECT * FROM livros;
SELECT titulo, autor, ano_publicacao FROM livros WHERE id = 1;

/*UPDATE livros SET autor = 'Osvaldo' WHERE id = 2;*/

/*DELETE FROM livros WHERE id = 2;*/