#language: pt

@regressivo
Funcionalidade: Migracao THAB

  @pre
  @migracao
  @boleto
  @migracaoControleTHAB
  Cenario: Migracao cliente THAB
    Dado que acesso a Loja Online
    E selecionar o "3" plano do carrossel da Home clicando no botão Eu quero! dele
    E validar que não há alterações no valor e/ou informações do Plano
    E preencho os campos ddd "", telefone "11947480168", email "claroqualidade3@gmail.com" e cpf "55762729001"
    E clicar no botão "Eu quero!"
    E preencho os campos Nome Completo "Isabelly Timberg", Data De Nascimento "20/02/2000" e Nome da Mãe "Marta Silva"
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
    E clicar no botão "Finalizar"
    Entao validar que não há alterações no valor e/ou informações do Plano na tela de parabens
