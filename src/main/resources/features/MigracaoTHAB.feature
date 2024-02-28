#language: pt

@regressivo
Funcionalidade: Migracao THAB

  @controle
  @migracao
  @migracaoPre
  @boleto
  @migracaoControleTHAB
  Cenario: Migracao cliente THAB
    Dado que acesso a Loja Online
    E selecionar o "4" plano do carrossel da Home clicando no botão Eu quero! dele
    E validar que não há alterações no valor e/ou informações do Plano
    E preencho os campos ddd "", telefone "11939340073", email "claroqualidade3@gmail.com" e cpf "204.707.172-03"
    E clicar no botão "Eu quero!"
    E preencho os campos Nome Completo "Ecomm Pre Bitencourt", Data De Nascimento "20/02/2000" e Nome da Mãe "Marta Silva"
    E preencho os campos "01001001", "288" e "CASA" no endereço
    E clicar no botão "Continuar"
    E seleciono a forma de pagamento
    E marco o checkbox de termos de aceite
    E clicar no botão "Continuar pagamento"
    E valido que foi ofertado plano de Controle Antecipado
    E clicar no botão "Eu quero! Controle Antecipado"
    E marco o checkbox de termos de aceite thab
    E clicar no botão "Continuar pagamento"
    E preencho o campo Código enviado Por SMS com o TOKEN recebido
    Quando clicar no botão "Finalizar"
    Entao validar que não há alterações no valor e/ou informações do Plano na tela de parabens