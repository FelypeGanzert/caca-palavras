# Caça Palavras
Api Rest para solucionar um Caça Palavras, que dado um tabuleiro e as palavras a serem encontradas seja retornado a posição das mesmas dentro do tabuleiro. Isso utilizando boas práticas de programação e implementando Testes.

## :rocket: Principais Tecnologias
* Java 11
* Maven
* Spring Framework (Boot, Web Rest, Data, Validation, Test)
* Hibernate
* Banco em memória H2
* PostgreSQL
* Lombok
* SpringFox para gerar a documentação com Swagger
* Pipeline de CI com GitHub Actions
* JUnit

## Regra de Negócio básica para resolver o caça palavras
O service para resolver o Caça Palavras está em CacaPalavrasResolverServiceImpl.
Existem alguns pontos principais que precisam ser considerados:

### Tabuleiro
O tabuleiro foi baseado no Plano Cartesiano, tendo o seu ponto inicial (1,1) e possuindo somente valores positivos que partem para a direita e para baixo com o padrão (x,y). Esquema do tabuleiro de forma visual:
<img src="/images/tabuleiro.png" alt="Tabuleiro"/>

### Busca
A busca é realizada partindo do ponto inicial e procurando para todas as 8 direções em relação daquele ponto por alguma formação de palavra possível. Sendo que é considerado a possibilidade de encontrar a mesma palavra em diferentes posições.
Por exemplo, digamos que tenha-se as palavras:
- Sol
- Céu
- Jogo
- Bolo
e foi criado um caça palavras com um tabuleiro, essas palavras seriam encontradas nas seguintes localizações do tabuleiro:
<img src="/images/resolvido.png" alt="Tabuleiro"/>

## Modelo de domínio
<img src="/images/dominio.png" alt="Tabuleiro"/>

## Fluxo padrão esperado
- Criar um caça palavras através de uma chamada POST para /api/caca-palavras;
- Adicionar as palavras que serão encontradas através de um POST para /api/caca-palavras/{idCacaPalavras}/palavras;
- Criar o tabuleiro através de um POST para /api/caca-palavras/{idCacaPalavras}/tabuleiro;
- Adicionar cada letra no tabuleiro. Isso pode ser feito letra por letra através de um POST para /api/caca-palavras/{idCacaPalavras}/tabuleiro/{idTabuleiro}/letras ou pode ser adicionados várias letras ao tabuleiro através de um POST  /api/caca-palavras/{idCacaPalavras}/tabuleiro/{idTabuleiro}/letras/adicionar-em-lote;
- Para então resolver o caça palavras é feito uma requisição PATCH para /api/caca-palavras/{id}/solucionar;
- Então caso seja feito uma requisição GET para /api/caca-palavras/{id} ele já irá passar a retornar o caça palavras resolvido.

## 🧪 Rodar os testes local
(É necessário ter o Maven instalado e configurado localmente)

    mvn test

## :rocket: Rodar a aplicação local
(É necessário ter o Maven instalado e configurado localmente)
Para rodar local basta rodar o comando abaixo na raiz do projeto:

    ./mvnw spring-boot:run

Como atualmente está definido em application.properties:

    spring.profiles.active=test

Será subida a aplicação localmente utilizando o Banco H2.

O projeto ficará então acessível em:

    http://localhost:8080/api/

---

<h4 align="center">
    Desenvolvido por Felype Ganzert - <a href="https://www.linkedin.com/in/felypeganzert/" target="_blank">Entre em Contato</a>
</h4>
