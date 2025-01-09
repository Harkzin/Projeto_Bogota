# language: pt
@Regressivo
@Web

Funcionalidade: ECCMAUT-145 - Migração Pré para Controle - Readequação Controle Fácil (Crédito Reprovado Passo 3)

  @Migracao
  @Controle
  @MigPreCtrlReadCtrlFacilCredReprovPasso3
  Cenario: Migração Pré para Controle - Readequação Controle Fácil (Crédito Reprovado Passo 3)

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Controle] do header
    Entao é direcionado para a PLP Controle

    Quando o usuário clicar no botão [Eu quero!] no card do plano "17528" da PLP
    Entao é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Migração], para o fluxo de troca de Plano
    E preenche os campos: [Telefone com DDD] "11947891723", [E-mail] e [CPF] "24412286843"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Entao é direcionado para a tela de Dados Pessoais
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "ECCMAUT CF", [Data de Nascimento] "20022000" e [Nome da Mãe] "Marta Silva"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP402"

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Entao é direcionado para a tela de Customizar Fatura
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
    Então é direcionado para a tela de SMS para o fluxo [Controle Fácil]
    E preenche o campo [Código de verificação] com o token recebido para fluxo [Controle Fácil]
      
    Quando o usuário clicar no botão [Finalizar][Continuar] da tela de SMS
    Então preenche o cartão com os dados: [Número] "5327060562905041", [Data de validade] "0525" e [CVV] "321"
    Então o usuario clicar em [Finalizar] na tela de pagamento [Controle Fácil]

    Entao é direcionado para a tela de Parabéns
    E os dados do pedido estão corretos