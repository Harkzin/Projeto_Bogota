#language: pt

@Web
Funcionalidade: ECCMAUT-1170 - Aquisicao Pos Aparelho - eSIM - Pix

  @PortPosAparEsimPix
  Cenario: Aquisicao Pos Aparelho - eSIM - Pix

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Entao é direcionado para a tela PLP de Aparelho

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Entao é direcionado para a PDP do Aparelho selecionado
    E seleciona a opção [Quero uma linha nova da Claro]
    E seleciona a plataforma [Claro Pós]
    E seleciona o plano "17524"
    E seleciona o chip [eSIM]

    Quando o usuário clicar no botão [Comprar] da PDP do Aparelho
    Entao é direcionado para a tela de Carrinho
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos: [Celular] "11987655678", [E-mail] e [CPF] para Pix

    Quando o usuário clicar no botão [Continuar] do Carrinho
    Entao é direcionado para a tela de Dados Pessoais
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "ECOMMAUT PORTCTRL APARESIM CC", [Data de Nascimento] "01011991" e [Nome da Mãe] "NOME MAE"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP202"
    E deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Entao é direcionado para a tela de Customizar Fatura
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    Então não deve haver alterações no valor e nem nas informações do Plano
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    Então não deve haver alterações no valor e nem nas informações do Plano
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar a forma de pagamento [Débito]
    #MOM-2021 Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra para Débito
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    #MOM-2021 Então não deve haver alterações no valor e nem nas informações do Plano
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    #MOM-2021 Então o valor do Plano será atualizado no Resumo da compra para fatura impressa
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar o método de recebimento da fatura [WhatsApp]
    #MOM-2021 Então o valor do Plano será atualizado no Resumo da compra para fatura digital
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Então será direcionado para a tela [Forma de Pagamento]
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Plano

