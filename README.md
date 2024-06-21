# booksapp
Repositório de teste de JDBC

## Codigos

Rodar Containers
```shell script
podman run --name meu-mysql -e MYSQL_ROOT_PASSWORD=minhasenha -p 3306:3306 -d mysql 
podman run --network minharede --name meu-mysql -e MYSQL_ROOT_PASSWORD=minhasenha -p 3306:3306 -d mysqlcustomizado:1.0
podman run --network minharede --name meu-backand -p 8081:8081 -d books-app-backend-jvm:1.0
```
Executa os comandos sql dentro do container
```shell script
podman exec -it meu-mysql mysql -uroot -p
```

Ver todos os containers que ja rodaram na maquina
```shell script
podman ps -a
```
Extrair uma ou mais imagens
```shell script
podman pull
```
Remove um ou mais containeres do host
```shell script
podman rm
```
Variavel de ambiente
```shell script
-e
```
Modo detected
```shell script
-d 
```
Porta
```shell script
-p
```

## Codigos Linux
```pwd - diretorio que estou
```ls - listor os arquivos de diretorio
```cd - entrar no diretorio
```cd .. - pra sair do diretorio
```podman build - construtor
```vi (nome do arquivo) - editor de texto top
...pra editar dentro do VI aperta I, pra salvar
   aperta ESC depois :wq
```cat (nome do arquivo) - mostrar ele na tela
```echo - imprimir na tela
```echo (texto) > (arquivo) - para jogar o que imprimiu na tela para dentro de um arquivo, se nao existe cria um novo
```podman logs (id) - ver os logs do container
```podman exec -it (nome do container) /bin/bash - para entrar no bash do container
```podman create network (nome da netwokr) - criar uma rede propria entre os containers
```npm start - iniciar o front
```ctrl C - parar os arquivos
```podman build -f podman/Containerfile -t mysqlcustomizado:1.0 . 


