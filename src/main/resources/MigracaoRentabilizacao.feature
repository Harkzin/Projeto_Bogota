#language: pt

@regressivo
Funcionalidade: Migracao Controle URL parametrizada para a oferta de rentabilizacao

  @controle
  @rentabilizacao
  @migracaoControle

  @debitoAutomatico
  @migracaoControleRentabilizacaoDA

  Cenario: Migracao Controle Rentabilizacao Débito automático
    Dado que acesso a URL parametrizada para a oferta de rentabilizacao
    E preencho os campos telefone "11939340063", email "claroqualidade3@gmail.com" e cpf "500.354.816-84"
    E clicar no botão "Eu quero!"
    E preencho os campos Nome Completo "Ecomm Pre Fagundes", Data De Nascimento "20/02/2000" e Nome da Mãe "Marta Silva"
    E preencho os campos "01001001", "288" e "CASA" no endereço
    E clicar no botão "Continuar"
    E seleciono a forma de pagamento
    E marco o checkbox de termos de aceite
    E clicar no botão "Continuar pagamento"
    E preencho o campo Código enviado Por SMS com o TOKEN recebido
    Quando clicar no botão "Finalizar"
    Entao validar que não há alterações no valor e/ou informações do Plano na tela de parabens

  @boleto
  @migracaoControleRentabilizacaoBoleto
  Cenario: Migracao Controle Rentabilizacao Boleto
    Dado que acesso a URL parametrizada para a oferta de rentabilizacao
    E preencho os campos telefone "11939340065", email "claroqualidade3@gmail.com" e cpf "646.456.257-64"
    E clicar no botão "Eu quero!"
    E preencho os campos Nome Completo "Ecomm Pre Marques", Data De Nascimento "20/02/2000" e Nome da Mãe "Marta Silva"
    E preencho os campos "01001001", "288" e "CASA" no endereço
    E clicar no botão "Continuar"
    E seleciono a forma de pagamento
    E marco o checkbox de termos de aceite
    E clicar no botão "Continuar pagamento"
    E preencho o campo Código enviado Por SMS com o TOKEN recebido
    Quando clicar no botão "Finalizar"
    Entao validar que não há alterações no valor e/ou informações do Plano na tela de parabens
