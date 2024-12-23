
#language: pt

@Web
Funcionalidade: ECCMAUT-196 - Carrinho com produtos diferentes

  @Acessorio
  @AcessCarrinhoComProdutosDiferentes
  Cenario: Carrinho com produtos diferentes

    Dado que o usuário acesse a Loja Online

    Quando o usuário clicar na opção [Acessórios] do header
    Então é direcionado para a tela PLP de Acessorios
    E deve ser exibido [Todas as Ofertas]
      #Será aberta uma task pro dev pra colocar ID nos acessórios, pois nenhum deles tem ID.

    Quando o usuário clicar no botão [Comprar] no produto [ACESSÓRIO] "CARREGADOR DE PAREDE CONCEPT"
    Então é direcionado para a PDP do Acessório selecionado
    E o usuário clicar no botão [Comprar] na PDP de Acessórios [ACESSÓRIO] "CARREGADOR DE PAREDE CONCEPT"

    Entao é direcionado para a tela de Carrinho de Acessórios

    Quando clicar no botão [Continuar comprando]
    Então é direcionado para a tela PLP de Acessorios
    E deve ser exibido [Todas as Ofertas]
      #Será aberta uma task pro dev pra colocar ID nos acessórios, pois nenhum deles tem ID.

    Quando o usuário clicar no botão [Comprar] no produto [ACESSÓRIO] "REPETIDOR MESH DECO M5 A C1300"
    Então é direcionado para a PDP do Acessório selecionado

    E o usuário clicar no botão [Comprar] na PDP de Acessórios [ACESSÓRIO] "REPETIDOR MESH DECO M5 A C1300"
    Entao é direcionado para a tela de Carrinho de Acessórios
    E preenche os campos: [Telefone com DDD] "11940662696", [CPF] "57037062898" e [E-mail] "teste@teste.com" para acessórios

    Quando o usuário clicar no botão [Continuar] da tela de Carrinho de Acessórios
    Entao é direcionado para a tela de Dados Pessoais
    E preenche os campos de dados pessoais: [Nome Completo] "ECCMAUT ACESS DOIS PROD PARCEL", [Data de Nascimento] "01011991" e [Nome da Mãe] "NOME MAE"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP202"

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Entao será direcionado para a tela [Forma de Pagamento]

    Quando o usuário clicar no botão [Adicionar cartão de crédito]
    Entao será exibido o iframe de pagamento do cartão
    E preenche os dados do cartão: [Nome] "ECOMM ACCEPT", [Número] "2223000250000004", [Data de validade] "0135", [CVV] "123" e [Parcelas] "2"

    Quando o usuário clicar no botão [Confirmar] do iframe do cartão da tela [Forma de Pagamento]
    Entao é direcionado para a tela de Parabéns
    E os dados do pedido estão corretos