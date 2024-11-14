# language: pt
@Regressivo @Web

Funcionalidade: ECCMAUT-529 - [Planos Base] Migração Pré Pago para Controle com impedimento por tempo de base

  @Migracao
  @Controle
  @MigracaoPreCtrlImpedidoTempoBase
  Cenario: Migração Pré Pago para Controle com impedimento por tempo de base

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Controle] do header
    E o usuário clicar no botão [Eu quero!] do card do plano "17528" na PLP Controle
    Entao é direcionado para a tela de Carrinho
      # Mas não deve haver alterações no valor e nem nas informações do Plano
    Quando seleciona a opção [Migração], para o fluxo de troca de Plano
    E preenche os campos: [Telefone com DDD] "11991736381", [E-mail] e [CPF] "08039137888"
    E o usuário clicar no botão [Eu quero!] do Carrinho
    Entao exibe a mensagem: O número informado não está ativo