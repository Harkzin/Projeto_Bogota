#language: pt

@Web
Funcionalidade: ECCMAUT-156 - Aquisição Controle + Readequação Controle Fácil - Crédito Reprovado

  @Aquisicao
  @Controle
  @AquisicaoCtrlCreditoReprovado
  Cenario: Aquisição Controle + Readequação Controle Fácil - Crédito Reprovado
      Dado que o usuário acesse a Loja Online
      Quando o usuário clicar na opção [Controle] do header
      Então é direcionado para a PLP Controle
      Quando o usuário clicar no botão [Eu quero!] no card do plano "17536" da PLP
      Entao é direcionado para a tela de Carrinho
   
