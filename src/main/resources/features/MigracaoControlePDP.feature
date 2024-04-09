#language: pt

@Regressivo
Funcionalidade: Migração de Plano Controle - PDP - Cliente Boleto para Débito

  @Migracao
  @Controle
  @MigracaoControlePDP
  Cenario: Migração de Plano Controle - PDP - Cliente Boleto para Débito
    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar no botão [Mais detalhes] do plano: "17270"
    Então é direcionado para a PDP do plano

    Quando o usuário selecionar a forma de pagamento: "Boleto"
    Então o valor do plano é atualizado

    Quando o usuário selecionar "Sem fidelidade"
    Então os aplicativos ilimitados são removidos da composição do plano

    Quando o usuário selecioanr a forma de pagamento: "Débito"
    E selecionar "Fidelidade de 12 meses"
    Então o valor do plano é atualizado
    E os aplicativos ilimitados são reexibidos na composição do plano

    Quando o usuário selecionar a forma de pagamento: "Boleto"
    Então o valor do plano é atualizado

    Quando o usuário clicar no botão Eu quero! da PDP
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nas informações do Plano
    E seleciona o fluxo "Migração/Troca"
    E preenche os campos: [Telefone com DDD] "11947486000", [E-mail] "claroqualidade3@gmail.com" e [CPF] "94516280884"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Customizar Fatura
      Mas não deve haver alterações no valor e nas informações do Plano
      E não deve ser exibido as opções de pagamento
      E não deve ser exibido os meios de recebimento da fatura
      E não deve ser exibido as datas de vencimento
    E seleciona a forma de pagamento: "Débito"
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Entao é direcionado para a tela de SMS
    E não deve haver alterações no valor e nas informações do Plano
    E preenche o campo [Código enviado Por SMS] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
    E não deve haver alterações no valor e nas informações do Plano
    E os dados do pedido estão corretos
