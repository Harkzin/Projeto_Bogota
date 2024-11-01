#language: pt

@Web
Funcionalidade: ECCMAUT-491 - Migracao - Downgrade Pos - Controle com Aceite de Multa

  #Massa: Pós - pagamento boleto - fatura correios
  @Teste
  @Migracao
  @Controle
  @MigracaoPosCtrlAceiteMulta
  Cenario: Migracao - Downgrade Pos - Controle com Aceite de Multa

    Dado que o usuário acesse a Loja Online
    Quando selecionar o Plano Controle de id "17536" na Home
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Migração], para o fluxo de troca de Plano
    E preenche os campos: [Telefone com DDD] "Pos" "Boleto" "Correios" comboMulti "false", [E-mail] e [CPF] multaServico "true" multaAparelho "false" dependente "false" claroClube "false" crivo "na"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então será exibido o modal [Aviso Troca de Plano]

    Quando o usuário clicar no botão [Confirmar] do modal [Aviso Troca de Plano]
    Então é direcionado pra tela de Customizar Fatura, com alerta de multa

    Quando o usuário clicar no botão [Concordo] da tela de multa
    Então é direcionado para a tela de Customizar Fatura
#     Mas não deve haver alterações no valor e nem nas informações do Plano
#    E deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada
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
#
#    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
#    Então não deve haver alterações no valor e nem nas informações do Plano
#
#    Quando o usuário selecionar o método de recebimento da fatura [Correios]
#    Então o valor do Plano será atualizado no Resumo da compra para fatura impressa
#
#    Quando o usuário selecionar o método de recebimento da fatura [WhatsApp]
#    Então o valor do Plano será atualizado no Resumo da compra para fatura digital
#    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Então é direcionado para a tela de SMS
    E preenche o campo [Código de verificação] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Então é direcionado para a tela de Parabéns
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos