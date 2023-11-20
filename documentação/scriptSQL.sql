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
Autor varchar(200) not null,
Editora varchar(200)not null,
Tipo varchar(200)not null,
nota smallint(2),
primary key (cod_livro)
);
select * from tabela_livros;
