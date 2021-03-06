# DBCompany Import - Java SE

Projeto desenvolvido para importação de arquvos ".data" e emissão de relatório com as seguintes informações: 
● Quantidade de clientes no arquivo de entrada
● Quantidade de vendedor no arquivo de entrada
● ID da venda mais cara - bônus (valor da venda R$)
● O pior vendedor - bônus (valor da venda R$)

# Instruções
### Execução
Clonar o projeto [import-dat](https://github.com/thiagohernandes/import-dat.git).

```sh
$ git clone https://github.com/thiagohernandes/import-dat.git
cd import-dat/prj-dbcompany
mvn clean package
cd target
java -jar prj-dbcompany-1.0-SNAPSHOT.jar 
```
### Arquivos
No diretório "import-dat\arquivos-teste-import" existem alguns arquivos que foram realizados os testes.
- Basta copiar ou arrastar os arquivos para o diretório home do Windows/Usuários "data/in" que em seguida, conforme desafio enviado, serão copiados para a pasta "data/out" renomeados no formato "nome-arquivo.done.dat";
- OBS: arquivos com nomes repetidos serão descartados caso sejam copiados novamente para o diretório;
- Atenção! Os arquivos serão excluídos do diretório "data/in" após serem importados;

### Testes
- foram utilizados: JUnit e Mockito nas principais classes de handler das linhas dos arquivos;
