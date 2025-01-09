# language: pt

@Regressivo
@Web
Funcionalidade: ECCMAUT-529 - Migracao Pre para Controle - Impedimento por tempo de base

  @Controle
  @Migracao
  @MigraPreCtrlImpedidoTempoBase
  Cenario: Migracao Pre Pago para Controle - Impedimento por tempo de base

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Controle] do header
    Entao é direcionado para a PLP Controle

    Quando o usuário clicar no botão [Eu quero!] no card do plano "17536" da PLP
    Entao é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Migração], para o fluxo de migração de plataforma
    E preenche os campos: [Telefone com DDD] "11991736381", [E-mail] e [CPF] "08039137888"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Entao será exibida a mensagem de erro: "O número informado não está ativo. Não fique sem falar, digite *552# em seu celular, e ative agora mesmo"