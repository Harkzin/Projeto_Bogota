#language: pt

@Web
Funcionalidade: ECCMAUT-258 - Migracao Controle para Pos - PDP

  #Massa: Controle - pagamento boleto - fatura impressa
  @Migracao
  @Pos
  @MigracaoCtrlPosPDP
  Cenario: Migracao Controle para Pos - PDP

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar no botão [Mais detalhes] do plano "17522"
#    Então é direcionado para a PDP do plano
#
#    Quando o usuário selecionar a forma de pagamento [Débito] na PDP
#    Então o valor do plano é atualizado
#
#    Quando o usuário selecionar a forma de pagamento [Boleto] na PDP
#    Então o valor do plano é atualizado
#
#    Quando o usuário selecionar a forma de pagamento [Débito] na PDP
#    Então o valor do plano é atualizado

    Quando o usuário clicar no botão [Eu quero!] da PDP
    Então é direcionado para a tela de Carrinho
#      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Migração], para o fluxo de migração de plataforma
    E preenche os campos: [Telefone com DDD] "Controle" "Boleto" "Correios" comboMulti "false", [E-mail] e [CPF] multaServico "false" multaAparelho "false" dependente "false" claroClube "false" crivo "na"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Customizar Fatura
#      Mas não deve haver alterações no valor e nem nas informações do Plano
#    E deve ser exibido as opções de pagamento, com a opção [Débito] selecionada
#    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
#    E deve ser exibido as datas de vencimento
#
#    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
#    Então não deve haver alterações no valor e nem nas informações do Plano
#
#    Quando o usuário selecionar o método de recebimento da fatura [Correios]
#    Então o valor do Plano será atualizado no Resumo da compra para fatura impressa
#
#    Quando o usuário selecionar a forma de pagamento [Boleto]
#    Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra para Boleto
#    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
#    E deve ser exibido as datas de vencimento
#
#    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
#    Então não deve haver alterações no valor e nem nas informações do Plano
#
#    Quando o usuário selecionar o método de recebimento da fatura [Correios]
#    Então não deve haver alterações no valor e nem nas informações do Plano
#
#    Quando o usuário selecionar a forma de pagamento [Débito]
#    #MOM-2021 Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra para Débito
#    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
#    E deve ser exibido as datas de vencimento
#    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Entao é direcionado para a tela de SMS
#      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [Código de verificação] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos