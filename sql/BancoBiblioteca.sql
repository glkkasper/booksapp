CREATE DATABASE biblioteca;
USE biblioteca;


CREATE TABLE autores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL
);
CREATE TABLE livros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    ano_publicacao INT,
    autor_id INT,
    numero_de_paginas INT,
    FOREIGN KEY (autor_id) REFERENCES autores(id)
);

INSERT INTO autores (nome, data_nascimento) VALUES ('Deus', '0000-01-01');
INSERT INTO autores (nome, data_nascimento) VALUES ('Gabriel', '1990-06-15');

INSERT INTO livros (titulo, ano_publicacao, numero_de_paginas, autor_id) VALUES ('Biblia', 1000, 1950, 1);
INSERT INTO livros (titulo, ano_publicacao, numero_de_paginas, autor_id) VALUES ('Caderno', 2023, 3000, 2);

SELECT * FROM livros;
SELECT * FROM autores;

--exemplo com JOIN
SELECT livros.id,livros.titulo,livros.ano_publicacao,livros.numero_de_paginas as 'Número de Paginas', autores.nome as autor FROM livros JOIN autores ON livros.autor_id = autores.id 
 WHERE titulo = 'Biblia';
/*UPDATE livros SET autor = 'Osvaldo' WHERE id = 2;*/

/*DELETE FROM livros WHERE id = 2;*/