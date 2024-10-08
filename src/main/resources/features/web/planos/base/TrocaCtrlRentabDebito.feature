#language: pt

@Web
Funcionalidade: ECCMAUT-259 - Troca Controle - Rentabilizacao - Debito

  #Massa: Controle - pagamento débito - fatura digital - sem multa ou com plano de menor valor ao que será usado

  @Troca
  @Controle
  @TrocaCtrlRentabDebito
  Cenario: Troca Controle - Rentabilizacao - Debito
    Dado que o usuário acesse a URL parametrizada de carrinho para a oferta de rentabilização "https://accstorefront.cokecxf-commercec1-s5-public.model-t.cc.commerce.ondemand.com/claro/pt/offer-plan/externalUri?offerPlanId=17536&coupon=65ae9c93a4ff011bb9e85d12ca567058c73f23dece9b43974ab86c2c927f933f&msisdn=msisdn&targetCampaign=migra&paymentMethod=debitcard&loyalty=true&invoiceType=DIGITAL&processType=MIGRATE"
    E preenche os campos: [Telefone com DDD] "11947726224", [E-mail] e [CPF] "93041484803"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Customizar Fatura
      #Mas não deve haver alterações no valor e nem nas informações do Plano
    E não deve ser exibido as opções de pagamento
    E não deve ser exibido os meios de recebimento da fatura
    E não deve ser exibido as datas de vencimento
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Então é direcionado para a tela de SMS
      #Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [Código de verificação] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Então é direcionado para a tela de Parabéns
      #Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos


  # Steps desabilitadas para cenários rentab. API para consulta das informações da promoção rentab com bug.