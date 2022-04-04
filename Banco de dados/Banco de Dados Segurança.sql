create database bdusuario;
Use bdusuario;


CREATE TABLE dadosusuario (
  `id` INT NOT NULL AUTO_INCREMENT, 
  `usuario` VARCHAR(60) NOT NULL ,
  `senha` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`))
  