#language: pt

@Regressivo
Funcionalidade: ECCMAUT-491 - Migracao - Downgrade Pos - Controle com Aceite de Multa

  #ECCMAUT-491
  #Massa: Pós - pagamento boleto - fatura correios

  @Migracao
  @Controle
  @MigracaoPosCtrlAceiteMulta
  Cenario: Migracao - Downgrade Pos - Controle com Aceite de Multa
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17536" do carrossel da Home
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Migração], para o fluxo de troca de Plano
    E preenche os campos: [Telefone com DDD] "11947726648", [E-mail] e [CPF] "73431972829"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então será exibido o modal [Aviso Troca de Plano]

    Quando o usuário clicar no botão [Confirmar] do modal [Aviso Troca de Plano]
    Então é direcionado pra tela de Customizar Fatura, com alerta de multa

    Quando o usuário clicar no botão [Concordo] da tela de multa
    Então é direcionado para a tela de Customizar Fatura
     Mas não deve haver alterações no valor e nem nas informações do Plano
     E deve ser exibido as opções de pagamento, com a opção [Débito] selecionada
     E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
     E deve ser exibido as datas de vencimento
    E preenche os dados bancários
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Entao é direcionado para a tela de SMS
    E preenche o campo [Código enviado Por SMS] com o token recebido
    Quando o usuário clicar no botão [Finalizar] da tela de SMS

    Entao é direcionado para a tela de Parabéns
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos