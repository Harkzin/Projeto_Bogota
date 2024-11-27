#language: pt

@Web
Funcionalidade: ECCMAUT-1339 - Aquisicao Controle com Aparelho - Credito Reprovado

  @Controle
  @Aquisicao
  @AquisCtrlAparCredRep
  Cenario: Aquisicao Controle com Aparelho - Credito Reprovado

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Entao é direcionado para a PLP de Aparelhos

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Entao é direcionado para a PDP do Aparelho selecionado
    E seleciona a opção [Quero uma linha nova da Claro]
    E seleciona a plataforma [Claro Controle]
    E seleciona o plano "17536"
    E seleciona o chip [Comum]

    Quando o usuário clicar no botão [Comprar] da PDP do Aparelho
    Entao é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos: [Celular] "", [E-mail] e [CPF] "" reprovado no crivo