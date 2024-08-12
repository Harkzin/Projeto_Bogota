#language: pt
@Regressivo
Funcionalidade: ECCMAUT-972 - Aquisicao Pos - 03 Dependente

  @Portabilidade
  @Pos
  @PortabilidadePos03DepPort
  Cenario: Aquisicao Pos - 03 Dependente
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17266" do carrossel da Home
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Aquisição]
    E preenche os campos: [Celular de contato] "11910211965", [E-mail] e [CPF] [CPF aprovado na clearSale? "true", CPF na diretrix? "false"]

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "ECOMMAUT PORTPOS TRESDEP", [Data de Nascimento] "01011991" e [Nome da Mãe] "NOME MAE"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP202"
      E deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então é direcionado para a tela de Dependentes
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E adiciona o primeiro dependente, com numero "910211964"
    E adiciona o segundo dependente, com numero "910211963"
    E adiciona o terceiro dependente, com numero "910211962"

    Quando o usuário clicar no botão [Continuar] na tela de Dependentes
    Então é direcionado para a tela de Customizar Fatura
      #Mas não deve haver alterações no valor e nem nas informações do Plano
      E deve ser exibido as opções de pagamento, com a opção [Débito] selecionada
      E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
      E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    #Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    #Então o valor do Plano será atualizado no Resumo da compra

    Quando o usuário selecionar a forma de pagamento [Débito]
    #Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    #Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    #Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar a forma de pagamento [Débito]
    #Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento
    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Então é direcionado para a tela de Parabéns
      #Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos

    #Steps desabilitados pelas validações do Resumo da compra não contemplarem as informções com depedentes ainda - ECCMAUT-351