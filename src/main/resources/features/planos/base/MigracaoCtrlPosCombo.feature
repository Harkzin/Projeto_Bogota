#language: pt

@Regressivo
Funcionalidade: Planos - Base

  #Massa: Controle Combo - pagamento boleto

  @Migracao
  @Pos
  @MigracaoCtrlPosCombo
  Cenario: Migracao Controle para Pos Combo Multi
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17266" do carrossel da Home
    Então é direcionado para a tela de Carrinho
    E seleciona a opção [Migração], para o fluxo de migração de plataforma
    E preenche os campos: [Telefone com DDD] "11947725638", [E-mail] e [CPF] "19398965178"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Termos Combo
    E o plano do carrinho será atualizado para o Plano Combo correspondente
      E não deve ser exibido as opções de pagamento
      E não deve ser exibido os meios de recebimento da fatura
      E não deve ser exibido as datas de vencimento
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos

    Entao é direcionado para a tela de SMS
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [Código enviado Por SMS] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos