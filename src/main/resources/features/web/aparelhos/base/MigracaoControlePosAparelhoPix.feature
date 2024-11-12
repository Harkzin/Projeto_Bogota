#language: pt

@Web
Funcionalidade: ECCMAUT-201 Migracao Controle Pos com Aparelho - Pix

  @Pos
  @Migracao
  @MigracaoControlePosAparelhoPix
  Cenario: Migracao Controle Pos com Aparelho - Pix

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Então é direcionado para a tela PLP de Aparelho

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Então é direcionado para a PDP do Aparelho selecionado

    Quando o usuário selecionar a opção [Manter meu número Claro], para o fluxo de Migração de Plano + Aparelho
    Então é exibido o popover para login
    E preenche o campo [Seu numero Claro] com "11945582776"

    Quando clicar no botão [Acessar] do popover
    Entao é exibido as opções e informações para cliente claro
    E seleciona [Mudar meu plano]
    E seleciona a plataforma [Claro Pós]
    E seleciona o plano "17515"

    Quando o usuário clicar no botão [Comprar] da PDP do Aparelho
    Entao é direcionado para a tela de Carrinho
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [E-mail]

    Quando o usuário clicar no botão [Continuar] do Carrinho
    Então é direcionado para a tela de Customizar Fatura
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada
    E não deve ser exibido os meios de recebimento da fatura
    E deve ser exibido as datas de vencimento

    Quando o usuário selecionar a forma de pagamento [Débito]
    #MOM-2021 Então o valor do Plano e o método de pagamento serão atualizados no Resumo da compra para Débito
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E não deve ser exibido os meios de recebimento da fatura
    E deve ser exibido as datas de vencimento
    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Entao é direcionado para a tela de SMS
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [Código de verificação] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao será direcionado para a tela [Forma de Pagamento]
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Plano

    E clicar no botão [Finalizar pedido com Pix] da tela [Forma de Pagamento]
    Então é direcionado para a tela de Parabéns Pix
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos