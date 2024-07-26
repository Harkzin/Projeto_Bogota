#language: pt

@Regressivo
Funcionalidade: Planos - Base

  #ECCMAUT-204

  @Pos
  @Troca
  @TrocaPosComDepAceiteMultaAparelho
  Cenario: Troca de Plano Pós Pago + Aparelho + Cliente Titular com Dependentes - Aceite multa de Aparelho

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Então é direcionado para a tela PLP de Aparelho

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Então é direcionado para a PDP do Aparelho selecionado