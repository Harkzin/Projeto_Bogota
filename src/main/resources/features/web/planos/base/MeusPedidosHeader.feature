#language: pt

@RegressivoConsulta
Funcionalidade: ECCMAUT-255 - Validar Meus Pedidos Header

  @MeusPedidosHeader
  Cenario: Validar Meus Pedidos Header
    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar no botão [Entrar] do header
    Entao é direcionado para a tela de opções da área logada

    Quando o usuário clicar na opção [Acompanhar pedidos]
    Entao é direcionado para a tela de login com CPF
    E preenche o campo [CPF] "73124213858"

    Quando o usuário clicar no botão [Continuar]
    Então é direcionado para a tela de opções de token

    Quando selecionar a opção [Receber código por e-mail] no e-mail "64475126aec442ce9ec10eb61a44bdd3@mailsac.com"
    Então é direcionado para a tela de token por email
    E preenche o campo [Digite o código recebido] com o token

    Quando o usuário clicar no botão [Confirmar]
    Então é direcionado para a tela Meus Pedidos
    E acessar o pedido mais recente, clicando no Número do pedido dele
    E validar que foi direcionado para a página de Detalhes de pedido