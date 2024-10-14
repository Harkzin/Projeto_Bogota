#language: pt

@Web
Funcionalidade: ECCMAUT-257 - Migracao Controle Pos - Rentabilizacao

  #Massa: Controle - pagamento boleto - fatura correios

  @Migracao
  @Pos
  @MigracaoCtrlPosRentab
  Cenario: Migracao Controle Pos - Rentabilizacao
    Dado que o usuário acesse a URL parametrizada de carrinho para a oferta de rentabilização "/claro/pt/offer-plan/externalUri?offerPlanId=17522&coupon=2f32ad67fb014807268c284b61facdfd&msisdn=msisdn&targetCampaign=migra&paymentMethod=debitcard&loyalty=true&invoiceType=DIGITAL&processType=MIGRATE"
    E preenche os campos: [Telefone com DDD] "11940660390", [E-mail] e [CPF] "36163924894"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Customizar Fatura
      #Mas não deve haver alterações no valor e nem nas informações do Plano
      E deve ser exibido as opções de pagamento, com a opção [Débito] selecionada
      E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
      E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    #Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    #Então o valor do Plano será atualizado no Resumo da compra

    Quando o usuário selecionar a forma de pagamento [Boleto]
    #Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    #Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    #Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar a forma de pagamento [Débito]
    #Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento
    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Entao é direcionado para a tela de SMS
      #Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [Código de verificação] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
      #Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos

  # Steps desabilitadas para cenários rentab. API para consulta das informações da promoção rentab com bug.