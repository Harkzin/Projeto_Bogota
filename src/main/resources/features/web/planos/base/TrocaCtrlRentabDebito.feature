#language: pt

@Web
Funcionalidade: ECCMAUT-259 - Troca Controle - Rentabilizacao - Debito

  #Massa: Controle - pagamento débito - fatura digital - sem multa ou com plano de menor valor ao que será usado

  @Troca
  @Controle
  @TrocaCtrlRentabDebito
  Cenario: Troca Controle - Rentabilizacao - Debito

    Dado que o usuário acesse a URL parametrizada para a oferta de rentabilização "/claro/pt/offer-plan/externalUri?offerPlanId=17528&coupon=cd3593b3dba52709ae89ae5aa1db8dbf61993f1047e42a2d3d942e9b77c2885b&msisdn=msisdn&targetCampaign=migra&paymentMethod=debitcard&loyalty=true&invoiceType=DIGITAL&processType=MIGRATE"
    Então é direcionado para a tela de Carrinho com a oferta
    E preenche os campos: [Telefone com DDD] "11947726224", [E-mail] e [CPF] "93041484803"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Customizar Fatura
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E não deve ser exibido as opções de pagamento
    E não deve ser exibido os meios de recebimento da fatura
    E não deve ser exibido as datas de vencimento
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura - Termos
    Então é direcionado para a tela de SMS
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [Código de verificação] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Então é direcionado para a tela de Parabéns
      #ECCMAUT351 Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos