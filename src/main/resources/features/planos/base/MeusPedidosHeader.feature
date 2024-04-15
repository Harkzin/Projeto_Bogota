#language: pt

@regressivo
Funcionalidade: Validar Meus Pedidos Header

  @home
  @validaMeusPedidos
  Cenario: Validar Meus Pedidos Header
    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar no botão [Entrar] do header
    Entao é direcionado para a tela de opções da área logada

    Quando o usuário clicar na opção [Acompanhar pedidos]
    Entao é direcionado para a tela de login com CPF
    E preenche o campo [CPF] "74338520698"

    Quando o usuário clicar no botão [Continuar]
    Então é direcionado para a tela de opções de token

    Quando selecionar a opção [Receber código por e-mail]
    Então é direcionado para a tela de token por email
    E preenche o campo [Digite o código recebido] com o código recebido

    Quando o usuário clicar no botão [Continuar]
    Então é direcionado para a tela Meus Pedidos
    E acessar o pedido mais recente, clicando no Número do pedido dele
    E validar que foi direcionado para a página de Detalhes de pedido
