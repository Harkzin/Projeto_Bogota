#language: pt

@Regressivo
@Web
Funcionalidade: ECCMAUT-301 - Bloqueio cliente Dependente

  @Controle
  @Troca
  @BloqueioClienteDependente
  Cenario: Bloqueio cliente Dependente

    Dado que o usuário acesse a Loja Online
    Quando selecionar o Plano Controle de id "17536" na Home
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Migração], para o fluxo de troca de Plano
    E preenche os campos: [Telefone com DDD] "11947626685", [E-mail] e [CPF] "48064730813"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Entao será exibida a mensagem de erro: "Verificamos que a linha informada é um dependente. Somente o titular pode realizar as alterações no plano."