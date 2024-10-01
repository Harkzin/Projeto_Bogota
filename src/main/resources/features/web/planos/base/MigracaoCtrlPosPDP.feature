#language: pt

@Web
Funcionalidade: ECCMAUT-258 - Migracao de Plano Controle - PDP - Cliente Boleto para Debito

  #Massa: Controle - pagamento boleto - fatura digital

  @Migracao
  @Pos
  @MigracaoCtrlPosPDP
  Cenario: Migracao de Plano Controle - PDP - Cliente Boleto para Debito
    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar no botão [Mais detalhes] do plano "17528"
    Então é direcionado para a PDP do plano

    Quando o usuário selecionar a forma de pagamento [Boleto] na PDP
    Então o valor do plano é atualizado

    Quando o usuário selecionar a forma de pagamento [Débito] na PDP
    Então o valor do plano é atualizado

    Quando o usuário selecionar a forma de pagamento [Boleto] na PDP
    Então o valor do plano é atualizado

    Quando o usuário clicar no botão [Eu quero!] da PDP
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Migração], para o fluxo de migração de plataforma
    E preenche os campos: [Telefone com DDD] "11947727032", [E-mail] e [CPF] "68647989848"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Customizar Fatura
      Mas não deve haver alterações no valor e nem nas informações do Plano
      E deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada
      E não deve ser exibido os meios de recebimento da fatura
      E deve ser exibido as datas de vencimento

    Quando o usuário selecionar a forma de pagamento [Débito]
    Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra para Débito
      E não deve ser exibido os meios de recebimento da fatura
      E deve ser exibido as datas de vencimento
    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Entao é direcionado para a tela de SMS
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [Código enviado Por SMS] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos
