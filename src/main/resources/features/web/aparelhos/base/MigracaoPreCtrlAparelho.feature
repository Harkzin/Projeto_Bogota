#language: pt

@Web
Funcionalidade: ECCMAUT-199 Migracao Pre Controle Com Aparelho

  @Migracao
  @Controle
  @MigracaoPreCtrlAparelho
  Cenario: Migracao Pre Controle Com Aparelho

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Então é direcionado para a tela PLP de Aparelho

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Então é direcionado para a PDP do Aparelho selecionado

    Quando o usuário selecionar a opção [Manter meu número Claro], para o fluxo de Migração de Plano + Aparelho
    Então é exibido o popover para login
    E preenche o campo [Seu numero Claro] com "11945583804"

    Quando clicar no botão [Acessar] do popover
    Entao é exibido as opções e informações para cliente claro
    E seleciona a plataforma [Claro Controle]
    E seleciona o plano "17528"

    Quando o usuário clicar no botão [Comprar] da PDP do Aparelho
    Entao é direcionado para a tela de Carrinho
      #Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos: [E-mail] e [CPF] "49014340800"

    Quando o usuário clicar no botão [Continuar] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
      #Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "ECCMAUT MIGRA PRE CTRL", [Data de Nascimento] "20022000" e [Nome da Mãe] "Marta Silva"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP402"

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então é direcionado para a tela de Customizar Fatura
      #Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    Então não deve haver alterações no valor e nem nas informações do Plano
      #Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    Então não deve haver alterações no valor e nem nas informações do Plano
      #Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar a forma de pagamento [Débito]
    #MOM-2021 Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra para Débito
      #Mas não deve haver alterações no valor e nem nas informações do Aparelho
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    #MOM-2021 Então não deve haver alterações no valor e nem nas informações do Plano
      #Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    Então o valor do Plano será atualizado no Resumo da compra para fatura impressa
      #Mas não deve haver alterações no valor e nem nas informações do Aparelho

    Quando o usuário selecionar o método de recebimento da fatura [WhatsApp]
    #MOM-2021 Então o valor do Plano será atualizado no Resumo da compra para fatura digital
      #Mas não deve haver alterações no valor e nem nas informações do Aparelho
    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Entao é direcionado para a tela de SMS
      #Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [Código de verificação] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao será direcionado para a tela [Forma de Pagamento]
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário clicar na aba [Cartão de crédito]
    E o usuário clicar no botão [Adicionar cartão de crédito]
    Então será exibido o iframe de pagamento do cartão
    E preenche os dados do cartão: [Nome] "ECCMAUT MIGRA PRE CTRL", [Número] "2223000250000004", [Data de validade] "0135", [CVV] "123" e [Parcelas] "2"

    Quando o usuário clicar no botão [Confirmar] do iframe do cartão da tela [Forma de Pagamento]
    Entao é direcionado para a tela de Parabéns
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos


