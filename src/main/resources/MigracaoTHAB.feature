#language: pt

@regressivo
Funcionalidade: Migracao THAB

  @migracao
  @controle
  @migracaoControleTHAB
  Cenario: Migracao cliente THAB
    Dado que acesso a Loja Online
    E selecionar um Plano Controle do carrossel da Home clicando no botão "Eu quero!" dele
    E validar que não há alterações no valor e/ou informações do Plano
    E selecionar a opção "Mudar meu plano da Claro"
    E preencho os campos ddd "", telefone "11947438199", email "claroqualidade3@gmail.com" e cpf "24014561208" no fluxo de "migracao"
    Quando clicar no botão "Eu quero!"
    Entao validar que é direcionado para pagina de "dados pessoais"
    E preencho os campos Nome Completo "ISABELLY TIMBERG", Data De Nascimento "20/02/2000" e Nome da Mãe "Marta Silva"
    E preencho os campos "01001001", "288" e "CASA" no endereço
    Quando clicar no botão "Continuar"
    Entao validar que é direcionado para pagina de "pagamento"
    Quando seleciono a forma de pagamento "Boleto"
#    E selecionar a fatura "email"
#    E selecionar a data de vencimento "4"
    E marco o checkbox de termos de aceite
    Quando clicar no botão "Continuar pagamento"
    Entao que sou redirecionado para a tela de "Controle Antecipado"
    Quando valido que foi ofertado plano de "Controle Antecipado"
    Entao clico no botão "EU QUERO!"
    Dado que sou redirecionado para a tela de "Customizar Fatura"
    Quando seleciono a forma de pagamento "Boleto"
    E marco o checkbox de termos de aceite
    Entao clico no botão "Continuar"
    Dado que sou redirecionado para a tela de "Token"
    Quando preencho o campo "Código enviado Por SMS" com o TOKEN recebido
    Entao clico no botão "Finalizar"
    Dado que sou redirecionado para a tela de "Parabéns"
    Entao validar que não há alterações no valor e/ou informações do Plano
