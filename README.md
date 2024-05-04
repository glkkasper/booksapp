# booksapp
Repositório de teste de JDBC

## codigos

rodar sql local
```shell script
podman run --name meu-mysql -e MYSQL_ROOT_PASSWORD=minhasenha -p 3306:3306 -d mysql 
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

## codigos LINUX 
```LINUX 
```pwd - diretorio que estou
```ls - listor os arquivos de diretorio
```cd - entrar no diretorio
```cd .. - pra sair do diretorio
```podman build - construtor
```vi (nome do arquivo) - editor de texto top
... pra editar dentro do VI aperta I, pra salvar
aperta ESC depois :wq
```cat (nome do arquivo) - mostrar ele na tela
```echo - imprimir na tela
```echo (texto) > (arquivo) - para jogar o que imprimiu na tela para dentro de um arquivo, se nao existe cria um novo
```podman exec -it (nome do container) /bin/bash - para entrar no bash do container



