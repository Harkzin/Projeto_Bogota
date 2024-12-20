#language: pt

@Regressivo
@Web
Funcionalidade: ECCMAUT-198 - [Aparelho Base] Migração Controle para Pós Pago com Aparelho 100% Claro Clube

  @Migracao
  @Controle
  @PosPago
  @Aparelho
  @100%ClaroClube
  Cenario: Migração Controle para Pós Pago com Aparelho 100% Claro Clube

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Então é direcionado para a PLP de Aparelhos

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Então é direcionado para a PDP do Aparelho selecionado

    Quando o usuário selecionar a opção [Manter meu número Claro], para o fluxo de Migração de Plano + Aparelho
    Então é exibido o popover para login
     E preenche o campo [Seu numero Claro] com "11947892295"
    E preenche o campo [Seu numero Claro] com "11947840913"
    E preenche o campo [Seu numero Claro] com "11947890768"
    E preenche o campo [Seu numero Claro] com "11947892217"
  
    Quando clicar no botão [Acessar] do popover
     Entao é exibido as opções e informações para cliente claro
    E seleciona [Mudar meu plano]
    E seleciona a plataforma [Claro Pós]
    E seleciona o plano "17515"


    Quando o usuário clicar no botão [Comprar] da PDP do Aparelho
    Entao é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [E-mail]

    Quando o usuário clicar no botão [Continuar] do Carrinho
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
#
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
    Entao é direcionado para a tela de SMS
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [Código de verificação] com o token recebido

    Quando o usuário clicar no botão [Continuar] da tela de SMS
    Entao será direcionado para a tela [Forma de Pagamento]
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário clicar na aba [Pix]
    E o usuário habilitar o desconto Claro Clube
    Entao o Aparelho receberá o desconto da pontuação
    
     E clicar no botão [Continuar] da tela [Forma de Pagamento]
     Entao é direcionado para a tela de Parabéns Claro Clube
     E os dados do pedido estão corretos