#language: pt

  @Regressivo
Funcionalidade: Planos - Base

  @Migracao
  @Controle
  @MigracaoPreCtrlTHAB
  Cenario: Migraçãao Pré - Readequação THAB
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17216" do carrossel da Home
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Migração], para o fluxo de migração de plataforma
    E preenche os campos: [Telefone com DDD] "11947720457", [E-mail] e [CPF] "57064187728"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "ECCMAUT THAB", [Data de Nascimento] "20022000" e [Nome da Mãe] "Marta Silva"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP 402"
      Mas não deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então é direcionado para a tela de Customizar Fatura
      Mas não deve haver alterações no valor e nem nas informações do Plano
      E deve ser exibido as opções de pagamento
      E deve ser exibido os meios de recebimento da fatura
      E deve ser exibido as datas de vencimento
    E seleciona a forma de pagamento: "Boleto"
    E seleciona o método de recebimento da fatura: "E-mail"
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Então é direcionado para a tela de readequação THAB

    Quando seleciona o plano de controle antecipado ofertado
    Então é direcionado para a tela de Customizar Fatura THAB
      Mas não deve haver alterações no valor e nem nas informações do Plano
      Mas não deve ser exibido as opções de pagamento
      E deve ser exibido os meios de recebimento da fatura
      Mas não deve ser exibido as datas de vencimento
    E seleciona o método de recebimento da fatura: "E-mail"
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Entao é direcionado para a tela de SMS
    E preenche o campo [Código enviado Por SMS] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos