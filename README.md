# System.out.println(Olá, mundo!)

Este é um projeto que foi desenvolvido em Java 17 e Spring como desafio técnico para um processo seletivo.
Trata-se de uma Wishlist, onde é possível adicionar um produto na lista do cliente, removê-lo, listar todos, ou apenas um produto caso ele esteja na lista.

Para que seja possível rodar este projeto, você precisa ter instalado em seu computador docker e docker-compose, pois é utilizado banco de dados MongoDB via container. Além disso, você pode rodar o app através da IDE de sua escolha, ou no terminal, mas para a segunda opção, você precisa ter o Maven e o OpenJDK 17 instalados na sua máquina.

Além disso, para testar o projeto, recomendo utilizar o Postman ou o Insomnia. Caso opte pelo Insomnia, tem uma collection disponível dentro da pasta "collection", na raiz do projeto, que vai facilitar sua vida! Você também pode testar usando curl ou outra ferramenta que gostar mais :) O importante é ser feliz!

***

## Como rodar o projeto:
* Na raiz do projeto, abra o terminal e rode o seguinte comando:
  * docker-compose up -d

Agora, com o docker rodando, faça um dos procedimentos abaixo:

1. Rodando pela IDE, basta apertar o botão "RUN"
2. Rodando pelo terminal, certifique-se que você possui instalado o maven com jdk17 instalado corretamente na sua máquina e execute o comando abaixo:
   * mvn exec:java -Dexec.mainClass="br.com.wishlist.WishlistApplication"
***

## Aqui estão as chamadas que são possíveis de realizar neste projeto:

* Onde houver {customerId} ou {productId}, é só substituir pelo valor correspondente.

### Adicionar um produto na lista do cliente:
  
~~~Request
POST /wishlist/{customerId}/products
Host: localhost:8080
Content-Type: application/json
{
  "productId":"id",
  "productName":"produto"
}
~~~

Se bem sucedida, a operação retornará ``200 OK`` e o produto adicionado à lista. Exemplo:
~~~Response
{
  "productId":"id",
  "productName":"produto"
}
~~~
---
### Consulta todos os produtos da lista do cliente:

~~~Request
GET /wishlist/{customerId}/products
Host: localhost:8080
~~~

Se bem sucedida, a operação retornará ``200 OK`` e a lista do cliente. Exemplo:
~~~Response
{
  "customerId": "1235",
  "wishProductsResponse": [
    {
	  "productId": "99546",
	  "productName": "teclado"
	}
  ]
}
~~~
---
### Consulta se um produto existe na lista do cliente:

~~~Request
GET /wishlist/{customerId}/products/{productId}
Host: localhost:8080
~~~
Se bem sucedida, a operação retornará ``200 OK`` e o produto na lista do cliente. Exemplo:
~~~Response
{
  "productId": "32145",
  "productName": "estojo"
}
~~~
---
### Remove um produto da lista do cliente:

~~~Request
DELETE /wishlist/{customerId}/products/{productId}
Host: localhost:8080
~~~
Se bem sucedida, a operação retornará ``204 No Content`` e não haverá conteúdo do body.

---

Este é o meu projeto! Fiz com muito carinho, caso você tenha chegado até aqui, muito obrigada! E se tiver dúvidas, pode enviar para staelsabrina@gmail.com, que eu ficarei feliz em responder :)