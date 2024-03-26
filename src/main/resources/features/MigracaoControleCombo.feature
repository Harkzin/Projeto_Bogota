#language: pt

@regressivo
Funcionalidade: Migracao Controle para Pos Combo Multi

  @pos
  @migracao
  @migracaoPos
  @boleto
  @migracaoControlecomboMulti
  Cenario: Migracao Controle para Pos Combo Multi
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17216" do carrossel da Home
    Então é direcionado para a tela de Carrinho
    E não deve haver alterações no valor e nas informações do Plano
    E seleciona o fluxo "Migração"
    E preenche os campos: Telefone com DDD "11947627466", E-mail "clordertest@mailsac.com" e CPF "33637686058"

    Quando o usuário clicar no botão Eu quero! do Carrinho
    Então é direcionado para a tela de Cliente Combo
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura
    Entao é direcionado para a tela de SMS
    E preenche o campo [Código enviado Por SMS] com o token recebido

    Quando o usuário clicar no botão [Continuar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
    E não deve haver alterações no valor e nas informações do Plano
    E os dados do pedido estão corretos