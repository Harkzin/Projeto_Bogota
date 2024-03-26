#language: pt

@regressivo
Funcionalidade: Migracao THAB

  @controle
  @migracao
  @migracaoPre
  @boleto
  @migracaoControleTHAB
  Cenario: Migracao cliente THAB
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "" do carrossel da Home
    Então é direcionado para a tela de Carrinho
    E não deve haver alterações no valor e nas informações do Plano
    E seleciona o fluxo "Migração"
    E preenche os campos: Telefone com DDD "", E-mail "clordertest@mailsac.com" e CPF ""

    Quando o usuário clicar no botão Eu quero! do Carrinho
    Então é direcionado para a tela de Dados Pessoais
    E não deve haver alterações no valor e nas informações do Plano
    E preenche os campos de informações pessoais: Nome Completo "ECOMMAUT AQSCTRLEXPRESSA", Data De Nascimento "01011991" e Nome da Mãe "Marta Silva"
    E preenche os campos de endereço: CEP "01001001", Número "65" e Complemento "AP202"

    Quando o usuário clicar no botão Continuar da tela de Dados Pessoais
    Então é direcionado para a tela de Customizar Fatura
    E não deve haver alterações no valor e nas informações do Plano
    E seleciona a forma de pagamento: "Boleto"
    E seleciona o método de recebimento da fatura: "E-mail"
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura
    Entao é direcionado para a tela de SMS
    E preenche o campo [Código enviado Por SMS] com o token recebido

    Quando o usuário clicar no botão [Continuar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
    E não deve haver alterações no valor e nas informações do Plano
    E os dados do pedido estão corretos