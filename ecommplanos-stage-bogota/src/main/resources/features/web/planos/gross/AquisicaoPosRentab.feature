#language: pt

@Web
Funcionalidade: ECCMAUT-1069 - Aquisicao Pos Pago - Rentabilizacao

  @Aquisicao
  @Pos
  @AquisicaoPosPagoRentab
  Cenario: Aquisicao Pos Pago - Rentabilizacao

    Dado que o usuário acesse a URL parametrizada para a oferta de rentabilização "/claro/pt/offer-plan/externalUri?offerPlanId=17522&coupon=ae3a66fc60cf93d5c6bd9cb212a8b67d&msisdn=msisdn&targetCampaign=gross&paymentMethod=ticket&loyalty=true&processType=ACQUISITION"
    Então é direcionado para a tela de Carrinho com a oferta
    E preenche os campos: [Celular de contato] "11999999988", [E-mail] e [CPF] [CPF aprovado na clearSale? "true", CPF na diretrix? "false"]

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "ECOMMAUT AQSPOS RENT", [Data de Nascimento] "01011991" e [Nome da Mãe] "NOME MAE"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP202"
    E deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então é direcionado para a tela de Dependentes

    Quando o usuário clicar no botão [Seguir sem dependentes]
    Então é direcionado para a tela de Customizar Fatura
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [App Minha Claro]
    Entao não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar a forma de pagamento [Débito]
    Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra para Débito
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    Então o valor do Plano será atualizado no Resumo da compra para fatura impressa

    Quando o usuário selecionar o método de recebimento da fatura [WhatsApp]
    Então o valor do Plano será atualizado no Resumo da compra para fatura digital
    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura - Termos
    Então é direcionado para a tela de Parabéns
      #ECCMAUT-1416 Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos