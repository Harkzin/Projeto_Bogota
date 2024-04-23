#language: pt

@Regressivo
Funcionalidade: Planos - Base

  @Troca
  @Controle
  @TrocaCtrlRentabilizacaoDebito
  Cenario: Troca Controle - Rentabilização - Débito
    Dado que o usuário acesse a URL parametrizada de carrinho para a oferta de rentabilização "/claro/pt/offer-plan/externalUri?offerPlanId=17218&coupon=09fd42fef86f8e0ea86d085f64a3696be6b4e91307c59913b172ddb5f60d0aaa&msisdn=msisdn&targetCampaign=migra&paymentMethod=debitcard&loyalty=true&invoiceType=DIGITAL&processType=MIGRATE"
    E preenche os campos: [Telefone com DDD] "11947629960", [E-mail] "clordertest@mailsac.com" e [CPF] "81371065373"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Customizar Fatura
      Mas não deve haver alterações no valor e nem nas informações do Plano
      Mas não deve ser exibido as opções de pagamento
      Mas não deve ser exibido os meios de recebimento da fatura
      Mas não deve ser exibido as datas de vencimento
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Entao é direcionado para a tela de SMS
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [Código enviado Por SMS] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos