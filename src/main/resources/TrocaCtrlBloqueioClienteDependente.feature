#language: pt

@regressivo
Funcionalidade: Troca Controle

  @troca
  @controle
  @bloqueioClienteDependente
  Cenario: Bloqueio cliente Dependente
    Dado que acesso a Loja Online
    E selecionar um Plano Controle do carrossel da Home clicando no botão "Eu quero!" dele
    E validar que não há alterações no valor e/ou informações do Plano
    E selecionar a opção "Mudar meu plano da Claro"
    E preencher os campos com os dados da linha Dependente
    Quando clicar no botão "Eu quero!"
    Então validar que foi exibida uma mensagem de erro e que não é possível avançar com o pedido