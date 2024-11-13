#language: pt

@Web
Funcionalidade: ECCMAUT-1171 - Portabilidade Controle com Aparelho - eSIM

  @Controle
  @Portabilidade
  @PortCtrlAparEsim
  Cenario: Portabilidade Controle com Aparelho - eSIM

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Entao é direcionado para a tela PLP de Aparelho

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Entao é direcionado para a PDP do Aparelho selecionado
    E seleciona a opção [Trazer meu número para Claro]
    E seleciona a plataforma [Claro Controle]
    E seleciona o plano "17536"
    E seleciona o chip [eSIM]

    Quando o usuário clicar no botão [Comprar] da PDP do Aparelho
    Entao é direcionado para a tela de Carrinho
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos: [Telefone a ser portado com DDD] "11913971002", [E-mail] e [CPF] [CPF aprovado na clearSale? "true", CPF na diretrix? "false"]

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

    Quando o usuário clicar no botão [Adicionar cartão de crédito]
    Então será exibido o iframe de pagamento do cartão
    E preenche os dados do cartão: [Nome] "ECCOM APROV", [Número] "2223000250000004", [Data de validade] "0135", [CVV] "123" e [Parcelas] "1"

    Quando o usuário clicar no botão [Confirmar] do iframe do cartão da tela [Forma de Pagamento]
    Então é direcionado para a tela de Parabéns
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Plano
    E clica no botão [Ok, Entendi] do modal de alerta de token
    E os dados do pedido estão corretos