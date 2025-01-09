#language: pt

@Regressivo
@Web
Funcionalidade: ECCMAUT-205 - Manter o Plano Pós Pago com fidelidade + Aparelho + Recusa multa de Aparelho

  @Pos
  @Troca
  @ManterPosFidelidadeAparelhoRecusaMultaAparelho
  Cenario: Migração Controle para Pós Pago com Aparelho 100% Claro Clube

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Então é direcionado para a PLP de Aparelhos

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    Então é direcionado para a PDP do Aparelho selecionado

    Quando o usuário selecionar a opção [Manter meu número Claro], para o fluxo de Manter o Plano + Aparelho
    Então é exibido o popover para login
    E preenche o campo [Seu numero Claro] com "11947892185"
    E clicar no botão [Acessar] do popover
    Entao é exibido as opções e informações para cliente claro

    Quando o usuário clicar no botão [Comprar] da PDP do Aparelho
    Entao é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Aparelho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche o campo [E-mail]

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura - Termos
    Então é direcionado pra tela de Customizar Fatura, com alerta de multa

    Quando clicar no botão [Não concordo]
    E clicar no botão [Ok, entendi]
    Então é direcionado para a Home