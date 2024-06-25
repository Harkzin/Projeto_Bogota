#language: pt

@Regressivo
Funcionalidade: Planos - Base

  @Migracao
  @Controle
  @MigracaoPosCtrlAceiteMulta
  Cenario: Migração - Downgrade Pós - Controle com Aceite de Multa
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17216" do carrossel da Home
    Então é direcionado para a tela de Carrinho
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Migração], para o fluxo de troca de Plano
    E preenche os campos: [Telefone com DDD] "11947726639", [E-mail] e [CPF] "73431972829"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho

    Quando o usuário clicar no botão [Condordo] Aviso Troca de Plano
    Então é direcionado pra tela de Customizar Fatura, com alerta de multa
    Quando o usuário clicar no botão [Concordo], Escolha a melhor dorma de pagamento para sua fatura
    Então é direcionado para a tela de Customizar Fatura
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E deve ser exibido as opções de pagamento
    E seleciona a forma de pagamento: "Débito"
    E preenche os dados bancários
    E marca o checkbox de termos de aceite
    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos

    Entao é direcionado para a tela de SMS
    E preenche o campo [Código enviado Por SMS] com o token recebido
    Quando o usuário clicar no botão [Finalizar] da tela de SMS