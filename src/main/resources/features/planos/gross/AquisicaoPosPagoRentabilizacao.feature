#language: pt
@Regressivo
Funcionalidade: Planos - Gross

  @Aquisicao
  @Pos
  @AquisicaoPosPagoRentabilizacao
  Cenario: Aquisição Pós Pago - Rentabilização
    Dado que o usuário acesse a URL parametrizada de carrinho para a oferta de rentabilização "/claro/pt/offer-plan/externalUri?offerPlanId=17218&coupon=09fd42fef86f8e0ea86d085f64a3696be6b4e91307c59913b172ddb5f60d0aaa&msisdn=msisdn&targetCampaign=gross&paymentMethod=debitcard&loyalty=true&invoiceType=DIGITAL&processType=MIGRATE"
    E preenche os campos: [Celular de contato] "11999999988", [E-mail] e [CPF] [CPF aprovado na clearSale? "true", CPF na diretrix? "false"]

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "ECOMMAUT AQSPOS RENT", [Data de Nascimento] "01011991" e [Nome da Mãe] "NOME MAE"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP202"
    E deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então é direcionado para a tela de Customizar Fatura
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E deve ser exibido as opções de pagamento
    E deve ser exibido os meios de recebimento da fatura
    E deve ser exibido as datas de vencimento
    E seleciona a forma de pagamento: "Débito"
    E preenche os dados bancários
    E seleciona o método de recebimento da fatura: "WhatsApp"
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Então é direcionado para a tela de Parabéns
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos
