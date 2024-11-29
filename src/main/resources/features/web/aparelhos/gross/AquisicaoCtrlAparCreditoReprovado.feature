#language: pt

@Web
Funcionalidade: ECCMAUT-1339 - Aquisicao Controle com Aparelho - Credito Reprovado

  @Controle
  @Aquisicao
  @AquisCtrlAparCredRep
  Cenario: Aquisicao Controle com Aparelho - Credito Reprovado

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Entao é direcionado para a PLP de Aparelhos

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Entao é direcionado para a PDP do Aparelho selecionado
    E seleciona a opção [Quero uma linha nova da Claro]
    E seleciona a plataforma [Claro Controle]
    E seleciona o plano "17536"
    E seleciona o chip [Comum]

    Quando o usuário clicar no botão [Comprar] da PDP do Aparelho
    Entao é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos: [Celular] "11987656765", [E-mail] e [CPF] "92337638898" reprovado no crivo

    Quando o usuário clicar no botão [Continuar] do Carrinho
    Entao é direcionado para a tela de Dados Pessoais
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "ECOMMAUT AQS CTRLAPAR CRED REP", [Data de Nascimento] "01011991" e [Nome da Mãe] "NOME MAE"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP202"
    E deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Entao é direcionado para a tela de Customizar Fatura
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [App Minha Claro]
    Entao não deve haver alterações no valor e nem nas informações do Plano
      Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    Entao não deve haver alterações no valor e nem nas informações do Plano
      Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    Entao não deve haver alterações no valor e nem nas informações do Plano
      Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar a forma de pagamento [Débito]
    Entao o valor do Plano e o método de pagamento serão atualizados no Resumo da compra para Débito
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [App Minha Claro]
    Entao não deve haver alterações no valor e nem nas informações do Plano
      Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    Entao não deve haver alterações no valor e nem nas informações do Plano
      Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    Entao o valor do Plano será atualizado no Resumo da compra para fatura impressa
      Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar o método de recebimento da fatura [WhatsApp]
    Entao o valor do Plano será atualizado no Resumo da compra para fatura digital
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura - Termos
    Entao é exibiba a mensagem de erro: "Infelizmente não foi possível realizar seu pedido por esse canal."