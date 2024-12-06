#language: pt

@Regressivo
@Web
Funcionalidade: ECCMAUT-1172 - Gerenciar eSIM - Header - Area Logada

  @GerenciarEsim
  Cenario: Gerenciar eSIM - Header - Area Logada

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar no botão [Entrar] do header
    Então é direcionado para a tela de opções da área logada

    Quando o usuário clicar na opção [Acompanhar pedidos eSIM]
    Então é direcionado para a tela de login eSIM com CPF
    E preenche o campo [CPF] "27910253834"

    Quando o usuário clicar no botão [Continuar]
    Então é direcionado para a tela de opções de token

    Quando o usuário selecionar a opção [Receber código por e-mail] no e-mail "64475126aec442ce9ec10eb61a44bdd3@mailsac.com"
    Então é direcionado para a tela de token por email
    E preenche o campo [Digite o código recebido] com o token

    Quando o usuário clicar no botão [Confirmar]
    Então é direcionado para a tela Minha Conta

    Quando o usuário selecionar a opção [Gerenciar eSIM]
    Então é direcionado para a tela Gerenciar eSIM

    Quando o usuário clicar no botão [Ativar eSIM]
    Então é direcionado para a tela de Ativar eSIM