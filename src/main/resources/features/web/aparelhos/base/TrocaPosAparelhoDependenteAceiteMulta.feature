#language: pt

@Web
Funcionalidade: ECCMAUT-204 Troca Pos Aparelho Aceite multa Dependente

  @Troca
  @Pos
  @TrocaPosAparAceiteMultaDependente
  Cenario: ECCMAUT-204 Troca Pos Aparelho Aceite multa Dependente

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Então é direcionado para a PLP de Aparelhos

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Então é direcionado para a PDP do Aparelho selecionado

    Quando o usuário selecionar a opção [Manter meu número Claro], para o fluxo de Migração de Plano + Aparelho
    Então é exibido o popover para login
    E preenche o campo [Seu numero Claro] com "11947890908"

    Quando clicar no botão [Acessar] do popover
    Entao é exibido as opções e informações para cliente claro
    E seleciona [Mudar meu plano]
    E seleciona a plataforma [Claro Pós]
    E seleciona o plano "17524"

    Quando o usuário clicar no botão [Comprar] da PDP do Aparelho
    Entao é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos: [E-mail] e [CPF] "69726368847"

    Quando o usuário clicar no botão [Continuar] do Carrinho
    Então é direcionado pra tela de Customizar Fatura, com alerta de multa

    Quando o usuário clicar no botão [Concordo] da tela de multa
    Então é direcionado para a tela de Customizar Fatura
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E não deve ser exibido as datas de vencimento

    Quando o usuário selecionar o método de recebimento da fatura [App Minha Claro]
    Entao não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar o método de recebimento da fatura [Correios]
    Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar a forma de pagamento [Débito]
    Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra para Débito
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E não deve ser exibido as datas de vencimento

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
    Então é direcionado para a tela de SMS
    E preenche o campo [Código de verificação] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao será direcionado para a tela [Forma de Pagamento]
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E o usuário clicar no botão [Adicionar cartão de crédito]
    E será exibido o iframe de pagamento do cartão
    E preenche os dados do cartão: [Nome] "ECCMAUT MIGRA PRE CTRL", [Número] "5435215759584731", [Data de validade] "0135", [CVV] "123" e [Parcelas] "2"

    Quando o usuário clicar no botão [Confirmar] do iframe do cartão da tela [Forma de Pagamento]
    Entao é direcionado para a tela de Parabéns
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos