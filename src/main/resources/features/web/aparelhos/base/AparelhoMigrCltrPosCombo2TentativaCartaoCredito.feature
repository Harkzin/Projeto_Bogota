#language: pt

@Web
Funcionalidade: ECCMAUT-199 Migracao Pre Controle Com Aparelho

  @Migracao
  @Controle
  @MigracaoPreCtrlAparelhoPoss
  Cenario: Migracao Pre Controle Com Aparelho

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Então é direcionado para a PLP de Aparelhos

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Então é direcionado para a PDP do Aparelho selecionado

    Quando o usuário selecionar a opção [Manter meu número Claro], para o fluxo de Migração de Plano + Aparelho
    Então é exibido o popover para login
    E preenche o campo [Seu numero Claro] com "11947890124"

    Quando clicar no botão [Acessar] do popover
    #Entao é exibido as opções e informações para cliente claro
    E seleciona [Mudar meu plano]
    E seleciona a plataforma [Claro Pós]
    E seleciona o plano "17530"

    Quando o usuário clicar no botão [Comprar] da PDP do Aparelho
    Entao é direcionado para a tela de Carrinho
      #Mas não deve haver alterações no valor e nem nas informações do Aparelho
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos: [E-mail] e [CPF] "64355377393"

    Quando o usuário clicar no botão [Continuar] do Carrinho
    Então é direcionado para a tela de Termos Combo
#    E o plano do carrinho será atualizado para o Plano Combo correspondente
    E não deve ser exibido as opções de pagamento
    E não deve ser exibido os meios de recebimento da fatura
    E não deve ser exibido as datas de vencimento
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura - Termos
    Entao é direcionado para a tela de SMS
    #Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [Código de verificação] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao será direcionado para a tela [Forma de Pagamento]
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Plano
    E o usuário clicar no botão [Adicionar cartão de crédito]
    E será exibido o iframe de pagamento do cartão
    E preenche os dados do cartão: [Nome] "REJECT", [Número] "5435215759584731", [Data de validade] "0135", [CVV] "123" e [Parcelas] "2"

    Quando o usuário clicar no botão [Confirmar] do iframe do cartão da tela [Forma de Pagamento]
    Então será exibido a mensagem de erro "Falha na autorização do pagamento"

    Quando o usuário clicar no botão [Tentar Novamente] da tela de [Forma Pagamento]
    Então será exibido o iframe de pagamento do cartão
    E preenche os dados do cartão: [Nome] "ECCMAUT MIGRA PRE CTRL", [Número] "5435215759584731", [Data de validade] "0135", [CVV] "123" e [Parcelas] "2"

    Quando o usuário clicar no botão [Confirmar] do iframe do cartão da tela [Forma de Pagamento]
    Entao é direcionado para a tela de Parabéns
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Aparelho
      #ECCMAUT-351 Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos
