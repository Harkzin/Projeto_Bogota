#language: pt

@Regressivo
Funcionalidade: Planos - Base

  @Migracao
  @Pos
  @MigracaoControlecomboMulti
  Cenario: Migracao Controle para Pos Combo Multi
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17270" do carrossel da Home
    Então é direcionado para a tela de Carrinho
    E não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Migração], para o fluxo de migração de plataforma
    E preenche os campos: [Telefone com DDD] "11947720623", [E-mail] e [CPF] "71371060568"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Termos Combo
    E marca o checkbox de termos de aceite
    E não deve ser exibido as opções de pagamento
    E não deve ser exibido os meios de recebimento da fatura
    E não deve ser exibido as datas de vencimento

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos

    Entao é direcionado para a tela de SMS
    E preenche o campo [Código enviado Por SMS] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
    E não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos