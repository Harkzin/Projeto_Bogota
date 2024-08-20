#language: pt

@RegressivoConsulta
Funcionalidade: ECCMAUT-517 - Aquisicao Controle Aparelho Sem Estoque

  @AparelhoSemEstoque
  Cenario: Aquisicao Controle Aparelho Sem Estoque
    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Então é direcionado para a tela PLP de Aparelho

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018061"
    Então é direcionado para a PDP do Aparelho selecionado

    Quando o usuário selecionar a cor variante do modelo "000000000000018062"
    Então é direcionado para a PDP do Aparelho selecionado
    E será informado que não há estoque