#language: pt

@Web
Funcionalidade: ECCMAUT-528 - Portabilidade Pos - Entrega Expressa - Mesmo endereco
  @Teste
  @Portabilidade
  @Pos
  @PortPosExpressaMesmoEnd
  Cenario: Portabilidade Pos - Entrega Expressa - Mesmo endereco

    Dado que o usuário acesse a Loja Online
    Quando selecionar o Plano Pós de id "17522" na Home
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Portabilidade]
    E preenche os campos: [Telefone a ser portado com DDD] Portabilidade, [E-mail] e [CPF] [CPF aprovado na clearSale? "true", CPF na diretrix? "false"]

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "ECOMMAUT PORTPOS MESMOEND", [Data de Nascimento] "01011991" e [Nome da Mãe] "NOME MAE"
    E preenche os campos de endereço: [CEP] expressa "01001900", [Número] "65" e [Complemento] "AP202"
    E deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então é direcionado para a tela de Dependentes
      Mas não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário clicar no botão [Seguir sem dependentes]
    Então é direcionado para a tela de Customizar Fatura
#      Mas não deve haver alterações no valor e nem nas informações do Plano
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
    Entao é direcionado para a tela de Parabéns
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Plano
    E clica no botão [Ok, Entendi] do modal de alerta de token
    E os dados do pedido estão corretos