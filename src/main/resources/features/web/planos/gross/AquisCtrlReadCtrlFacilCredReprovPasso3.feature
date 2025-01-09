# language: pt
@Regressivo
@Web

Funcionalidade: ECCMAUT-156 - Aquisição Controle + Readequação Controle Fácil - Crédito Reprovado (Passo 3)

  @Aquisicao
  @Controle
  @ReadequacaoControleFacil
  @CreditoReprovado
  @AquisCtrlReadCtrlFacilCredReprovPasso3
  Cenario: Aquisicao Controle - Readequacao Controle Facil (Credito Reprovado Passo 3)

    Dado que o usuário acesse a Loja Online
    Quando selecionar o Plano Controle de id "17536" na Home
    Então é direcionado para a tela de Carrinho
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Aquisição]
    E preenche os campos: [Celular] "11970901243", [E-mail] e [CPF] "16061980841" reprovado no crivo

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "MARIA DA SILVA", [Data de Nascimento] "01011991" e [Nome da Mãe] "NOME MAE"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP202"
    E deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então é direcionado para a tela de Customizar Fatura
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [App Minha Claro]
    Entao não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar a forma de pagamento [Débito]
    Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra para Débito
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [App Minha Claro]
    Entao não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    Então o valor do Plano será atualizado no Resumo da compra para fatura impressa

    Quando o usuário selecionar o método de recebimento da fatura [WhatsApp]
    Então o valor do Plano será atualizado no Resumo da compra para fatura digital
    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura - Termos
    Então é direcionado para a tela de readequação Controle Fácil

    Quando o usuário selecionar o plano de controle fácil
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o cartão com os dados: [Número] "5327060562905041", [Data de validade] "0525" e [CVV] "321"
    Então o usuario clicar em [Finalizar] na tela de pagamento [Controle Fácil]

    Então é direcionado para a tela de Parabéns
#      ECCMAUT-1416 Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos