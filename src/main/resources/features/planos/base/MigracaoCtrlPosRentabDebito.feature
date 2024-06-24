#language: pt

@Regressivo
Funcionalidade: Planos - Base

  #Massa: Controle - pagamento boleto - fatura correios

  @Migracao
  @Pos
  @MigracaoCtrlPosRentabDebito
  Cenario: Migraçao Controle Pós - Rentabilização - Débito
    Dado que o usuário acesse a URL parametrizada de carrinho para a oferta de rentabilização "/claro/pt/offer-plan/externalUri?offerPlanId=17270&coupon=c7c21130f16ac009e7f4819ef1e80611&msisdn=msisdn&targetCampaign=migra&paymentMethod=debitcard&loyalty=true&invoiceType=DIGITAL&processType=MIGRATE"
    E preenche os campos: [Telefone com DDD] "11947726224", [E-mail] e [CPF] " 93041484803"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Customizar Fatura
      #Mas não deve haver alterações no valor e nem nas informações do Plano
      Mas deve ser exibido as opções de pagamento
      Mas deve ser exibido os meios de recebimento da fatura
      Mas deve ser exibido as datas de vencimento
    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Entao é direcionado para a tela de SMS
      #Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [Código enviado Por SMS] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
      #Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos

  # Mas não deve haver alterações no valor...
  # Desativado para cenários rentab,
  # API para consulta das informações da promoção rentab com bug.