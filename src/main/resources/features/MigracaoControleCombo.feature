#language: pt

@regressivo
Funcionalidade: Migracao Controle para Pos Combo Multi

  @pos
  @migracao
  @migracaoPos
  @boleto
  @migracaoControlecomboMulti
  Cenario: Migracao Controle para Pos Combo Multi
    Dado que acesso a Loja Online
    E selecionar o plano de id "17270" do carrossel da Home clicando no botão Eu quero! dele
    E validar que não há alterações no valor e nas informações do Plano
    E preencho os campos Telefone com DDD "11947620656", E-mail "claroqualidade3@gmail.com" e CPF "330.568.571-98"
    E clicar no botão "Eu quero!"
    E validar a mensagem de cliente combo multi
    E marco o checkbox de termos de aceite thab
    E clicar no botão "Continuar pagamento"
    E preencho o campo Código enviado Por SMS com o TOKEN recebido
    Quando clicar no botão "Finalizar"
    Entao validar que não há alterações no valor e/ou informações do Plano na tela de parabens
