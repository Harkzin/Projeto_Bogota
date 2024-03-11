#language: pt

@regressivo
Funcionalidade: Troca Controle - Recusa multa

  @controle
  @troca
  @TrocaCtrlRecusaMulta
  Cenario: Troca Controle recusa multa
    Dado que acesso a Loja Online
    E selecionar o "1" plano do carrossel da Home clicando no botão Eu quero! dele
    E validar que não há alterações no valor e nas informações do Plano
    E preencho os campos Telefone com DDD "11947431002", E-mail "claroteste61@gmail.com" e CPF "64526647861"
    E clicar no botão "Eu quero!"
    E clicar no botão "Não concordo"
    Quando clicar no botão "Ok, entendi"
    Então  validar que foi direcionado para a Home
