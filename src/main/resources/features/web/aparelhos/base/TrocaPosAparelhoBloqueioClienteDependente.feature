#language: pt

@Regressivo
@Web
Funcionalidade: ECCMAUT-513 Troca Pos Aparelho Bloqueio Cliente Dependente

  @Troca
  @Pos
  @TrocaPosBloqueioClienteDependente
  Cenario: Troca Pos Aparelho Bloqueio Cliente Dependente

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Então é direcionado para a PLP de Aparelhos

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Então é direcionado para a PDP do Aparelho selecionado

    Quando o usuário selecionar a opção [Manter meu número Claro], para o fluxo de Migração de Plano + Aparelho
    Então é exibido o popover para login
    E preenche o campo [Seu numero Claro] com "11947729399"

    Quando clicar no botão [Acessar] do popover
    Entao será exibida a mensagem de erro para dependente: "Verificamos que a linha informada é um dependente. Favor informar a linha titular."