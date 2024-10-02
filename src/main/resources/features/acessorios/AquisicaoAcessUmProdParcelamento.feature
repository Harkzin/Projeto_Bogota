#language: pt

@Regressivo
Funcionalidade: ECCMAUT-1092 - Aquisicao de acessorio com 1 produto - Parcelamento

  @Acessorio
  @AcessUmProdutoParcel
  Cenario: Aquisicao de acessorio com 1 produto - Parcelamento

    #Não foram feitas as validações necessárias, pois a parte de validações está com a task ECCMAUT-351
    Dado que o usuário acesse a Loja Online

    Quando o usuário clicar na opção [Acessórios] do header
    Então é direcionado para a tela PLP de Acessorios
    E deve ser exibido [Todas as Ofertas]

    Quando o usuário clicar no botão [Comprar] no produto [Repetidor Mesh Deco]
    Quando o usuário clicar no botão [Comprar] no produto [CARREGADOR DE PAREDE CONCEPT]
    Então é direcionado para a PDP do Acessório selecionado
    E o usuário clicar no botão [Comprar] na PDP de Acessórios

    Quando o usuário clicar no botão [Comprar] na PDP de Acessórios
    Entao é direcionado para a tela de Carrinho de Acessórios

    Quando o usuário preencher os campos de acessorios: [Telefone com DDD] "11940662696", [CPF] "57037062898" e [E-mail] "teste@teste.com"
    Quando é direcionado para a tela de Carrinho de Acessórios
    E o usuário preencher os campos de acessorios: [Telefone com DDD] "11940662696", [CPF] "57037062898" e [E-mail] "teste@gmail.com"
    E clicar no botão [Continuar]
    Entao é direcionado para a tela de Dados Pessoais
    E preenche os campos de dados pessoais: [Nome Completo] "ECCMAUT ACESS UM PROD PARCEL", [Data de Nascimento] "01011991" e [Nome da Mãe] "NOME MAE"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP202"

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Entao será direcionado para a tela [Forma de Pagamento] de acessórios
    E o usuário clicar no botão [Adicionar cartão de crédito]
    E será exibido o iframe de pagamento do cartão
    E preenche os dados do cartão: [Nome] "ECCMAUT ACESS UM PROD PARCEL", [Número] "2223000250000004", [Data de validade] "0135", [CVV] "123" e [Parcelas] "2"

    Quando o usuário clicar no botão [Confirmar] do iframe do cartão da tela [Forma de Pagamento]
    Entao é direcionado para a tela de Parabéns