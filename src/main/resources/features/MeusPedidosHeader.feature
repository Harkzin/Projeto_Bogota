#language: pt

@regressivo
Funcionalidade: Validar Meus Pedidos Header

  @home
  @validaMeusPedidos
  Cenario: Validar Meus Pedidos Header
    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar no botão: [Entrar]
    Entao é direcionado para a tela de Login

    Quando o usuário clicar na opção: [Acompanhar pedidos]
    Entao é direcionado para a tela de acompanhamento do usuário

    Quando o usuário clicar na opção: [Acompanhar Meus Pedidos]
    Então  é direcionado para a tela de Pedidos
    E preenche o campo: [CPF] da página de login de Pedidos

    Quando o usuário clica no botão: [Continuar]
    Então é direcionado para a tela de Token da tela de Meus pedidos
    E seleciona a forma de recebimento do token: [E-mail]
    E é direcionado para a tela de token

    Quando o usuário clicar no botão: [Continuar]
    Então preenche o campo Código Token

    Quando o usuário clicar no botão: [Continuar]
    Então é direcionado para a tela: Meus Pedidos

    E validar que foi exibido o Número do pedido, Data do pedido e o Status do(s) pedido(s) já realizado(s) anteriormente
    E acessar o pedido mais recente, clicando no Número do pedido dele
    E validar que foi direcionado para a página de acompanhamento de pedido
