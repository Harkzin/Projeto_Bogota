#language: pt

@regressivo
Funcionalidade: Troca Controle - Bloqueio Cliente Dependente

  @controle
  @troca
  @bloqueioClienteDependente
  Cenario: Bloqueio cliente Dependente
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17218" do carrossel da Home
    Então é direcionado para a tela de Carrinho
    E não deve haver alterações no valor e nas informações do Plano
    E seleciona o fluxo "Migração"
    E preenche os campos: Telefone com DDD "11947626685", E-mail "clordertest@mailsac.com" e CPF "48064730813"

    Quando o usuário clicar no botão Eu quero! do Carrinho
    E será exibida a mensagem de erro: "Verificamos que a linha informada é um dependente. Somente o titular pode realizar as alterações no plano."