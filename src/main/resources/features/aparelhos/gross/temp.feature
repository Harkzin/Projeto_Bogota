#language: pt

Funcionalidade: Aparelhos - Gross

  @AquisicaoCtrlAparelho
  Cenario: Aquisicao Controle com Aparelho
    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Então é direcionado para a tela PLP de Aparelho

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Então é direcionado para a PDP do Aparelho selecionado
    E seleciona a opção [Quero uma linha nova da Claro]

    Quando o usuário clicar no botão [Comprar] da PDP do Aparelho
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos: [Celular de contato] "11999999988", [E-mail] e [CPF] [CPF aprovado na clearSale? "true", CPF na diretrix? "false"]

    Quando o usuário clicar no botão [Continuar] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "ECOMMAUT AQSCTRL EXPRESBOL", [Data de Nascimento] "01011991" e [Nome da Mãe] "NOME MAE"
    E preenche os campos de endereço: [CEP] convencional "01001000", [Número] "65" e [Complemento] "AP202"
      E deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então é direcionado para a tela de Customizar Fatura
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
      E deve ser exibido as opções de pagamento, com a opção [Débito] selecionada
      E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
      E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    Então o valor do Plano será atualizado no Resumo da compra

    Quando o usuário selecionar a forma de pagamento [Boleto]
    Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra
      E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
      E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar a forma de pagamento [Débito]
    Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra
      E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
      E deve ser exibido as datas de vencimento
    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Então será direcionado para a tela [Forma de Pagamento]
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário clicar no botão [Adicionar cartão de crédito]
    Entao será exibido o iframe de pagamento do cartão
    E preenche os dados do cartão: [Nome] "ECCOM APROV", [Número] "2223000250004", [Data de validade] "0135", [CVV] "123" e [Parcelas] "1"

    Quando o usuário clicar no botão [Confirmar] do iframe do cartão da tela [Forma de Pagamento]
    Entao é direcionado para a tela de Parabéns
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos