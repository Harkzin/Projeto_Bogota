#language: pt

@Regressivo
Funcionalidade: Planos - Base

  @Controle
  @Troca
  @TrocaCtrlRecusaMulta
  Cenario: Troca Controle recusa multa
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17216" do carrossel da Home
    Então é direcionado para a tela de Carrinho
    E não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Migração], para o fluxo de troca de Plano
    E preenche os campos: [Telefone com DDD] "11947629986", [E-mail] e [CPF] "46768527703"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado pra tela de Customizar Fatura, com alerta de multa
    E clicar no botão [Não concordo]

    Quando clicar no botão [Ok, entendi]
    Então é direcionado para a Home