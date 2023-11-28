CREATE DATABASE teste;
use teste;
CREATE TABLE tabela_cadastro(
cod_pessoa INT AUTO_INCREMENT,
nome VARCHAR(200) NOT NULL,
senha VARCHAR(200) NOT NULL,
idade int(3) NOT NULL,
sexo varchar(9) NOT NULL,
telefone int (9) NOT NULL,
email varchar (200) NOT NULL,
livrosFavoritos1 varchar(20) NOT NULL,
livrosFavoritos2 varchar(20) NOT NULL,
autoridade varchar(20) NOT NULL,
PRIMARY KEY (cod_pessoa)
);
select * from tabela_cadastro;
CREATE TABLE tabela_livros(
cod_livro INT AUTO_INCREMENT,
Titulo varchar(200) not null,
Descrição text not null,
Autor varchar(200) not null,
Editora varchar(200)not null,
Tipo varchar(200)not null,
primary key (cod_livro)
);
select * from tabela_livros;
create table Nota_livros(
Titulo varchar(200) not null,
SomaNotas int,
Qtd_leitores int,
Nota_media float,
primary key(Titulo)
);
select * from Nota_livros;

DELIMITER //
CREATE TRIGGER adicionar_livro_nota
AFTER INSERT ON tabela_livros
FOR EACH ROW
BEGIN
    INSERT INTO Nota_livros (Titulo, SomaNotas, Qtd_leitores)
    VALUES (NEW.Titulo, 0, 0);
END;
//
DELIMITER ;



