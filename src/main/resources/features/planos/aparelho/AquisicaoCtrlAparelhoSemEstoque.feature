#language: pt

@Regressivo
Funcionalidade: Aquisicao Controle com credito reprovado

  @Controle
  @Aquisicao
  @AqCtrlAparelhoSemEstoque

  Cenario: Aquisicao Controle Aparelho Sem Estoque

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar no botão [Celulares] do header
    Então é direcionado para a tela para tela PLP de Aparelho

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "18061"
    Então é direcionado para a tela para tela PDP de Aparelho selecionado

    Quando o úsuario seleciono a cor [Azul Safira] do aparelho
    Então será informado que não há estoque