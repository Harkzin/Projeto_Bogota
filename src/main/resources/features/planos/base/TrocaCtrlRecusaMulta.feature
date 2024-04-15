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
    E seleciona o fluxo "Migração/Troca"
    E preenche os campos: [Telefone com DDD] "11947626673", [E-mail] "clordertest@mailsac.com" e [CPF] "48064730813"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado pra tela de Customizar Fatura, com alerta de multa
    E clicar no botão [Não concordo]

    Quando clicar no botão [Ok, entendi]
    Então é direcionado para a Home