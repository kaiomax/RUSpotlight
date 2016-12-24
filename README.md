# RU Spotlight 
Uma aplicação para dar voz à opinião dos usuários do Refeitório Universitário - UFRN

![alt tag](https://raw.githubusercontent.com/kaiomax/RUSpotlight/master/resources/tabs.jpg)
![alt tag](https://raw.githubusercontent.com/kaiomax/RUSpotlight/master/resources/meal.jpg)

## Objetivo
A aplicação surgiu como proposta de execução de projeto a ser submetido em avaliação para o cumprimento da matéria Desenvolvimento de Sistemas para Dispositivos Móveis, com o objetivo de possibilitar a automatização das pesquisas de opinião referentes as refeições do Refeitório Universitário da Universidade Federal do Rio Grande do Norte.

## Funcionalidades
* Automatização das pesquisas de opinião
* Avaliação das refeições
* Comentários sobre as refeições
* Visualização das estatísticas de avalições por meio de gráficos
* Consulta ao cardápio de cada refeição

## Tecnologias envolvidas
* [Android Studio + SDK](https://developer.android.com/studio/index.html)
* [Firebase](https://firebase.google.com/)
* [API.sistemas UFRN](https://api.ufrn.br/)
* [Retrofit](https://square.github.io/retrofit/)

### Configuração
1. Clone o repositório
2. Crie um novo projeto no Firebase e adicione o arquivo `google-services.json` gerado no diretório `/app`
3. Na seção **Realtime Database** do projeto no *Console* do Firebase importe o arquivo `sample-data.json` disponível na raiz do projeto *RUSpotlight*
4. Importe o projeto pelo Android Studio e execute a aplicação

#### Comunicação com a API.sistemas
Para obter os dados do cartão do usuário é necessário cadastrar uma nova aplicação na [API.sistemas](https://api.ufrn.br/) (seção Cadastro da Aplicação) e adicionar as credenciais na classe `UFRNClient`. Substituindo os valores das constantes `CLIENT_ID` e `CLIENT_SECRET`.

## Licença

MIT © [Kaio Max](https://github.com/kaiomax)

## TO-DO
* Refatorar o código do projeto
* Adicionar estrutura de dados do cardápio para cada refeição
* Associar o comentário ao usuário
* Possibilitar ação de curtir comentário
* Filtrar dados das avaliações por semana e mês
* Adicionar gráfico do tipo coluna/linha para filtro semanal e mensal das estatísticas
* Listar refeições avaliadas no perfil do usuário
* Melhorar funcionalidade de associação do cartão do usuário
* Melhorar visualização de refeição ativa
* Adicionar imagens de destaque para cada tipo de refeição