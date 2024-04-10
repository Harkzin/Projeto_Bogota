#language: pt

@regressivo
Funcionalidade: Migracao THAB

  @migracao
  @controle
  @migracaoControleTHAB
  Cenario: Migracao cliente THAB
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17216" do carrossel da Home
    Então é direcionado para a tela de Carrinho
    E não deve haver alterações no valor e nas informações do Plano
    E seleciona o fluxo "Migração/Troca"
    E preenche os campos: [Telefone com DDD] "11947628552", [E-mail] "clordertest@mailsac.com" e [CPF] "33006780213"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
    E não deve haver alterações no valor e nas informações do Plano
    E preenche os campos de informações pessoais: Nome Completo "ECOMM MAURO THAB", Data De Nascimento "20022000" e Nome da Mãe "Marta Silva"
    E preenche os campos de endereço: [CEP] "convencional", [Número] "65" e [Complemento] "AP 402"

    Quando o usuário clicar no botão Continuar da tela de Dados Pessoais
    Então é direcionado para a tela de Customizar Fatura
    E não deve haver alterações no valor e nas informações do Plano
    E deve ser exibido as opções de pagamento
    E deve ser exibido os meios de recebimento da fatura
    E deve ser exibido as datas de vencimento
    E seleciona a forma de pagamento: "Boleto"
    E seleciona o método de recebimento da fatura: "E-mail"
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Então é direcionado para a tela de THAB
    
    Quando seleciona o plano de controle antecipado ofertado, clicando no botão [Eu Quero!] dele
    Então é direcionado para a tela de fatura Cliente THAB
    E não deve ser exibido as opções de pagamento
    E não deve ser exibido as datas de vencimento
    E seleciona o método de recebimento da fatura: "E-mail"
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Entao é direcionado para a tela de SMS
    E preenche o campo [Código enviado Por SMS] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
    E não deve haver alterações no valor e nas informações do Plano
    E os dados do pedido estão corretos