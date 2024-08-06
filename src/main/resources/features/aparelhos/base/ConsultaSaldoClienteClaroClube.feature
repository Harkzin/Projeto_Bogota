#language: pt

@RegressivoConsulta
Funcionalidade: Consulta de saldo Claro Clube - Cliente  Claro Clube - Header

  @ConsultaSaldoClienteClaroClube
  Cenario: Consulta de saldo Claro Clube - Cliente  Claro Clube - Header
    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar no botão [Entrar] do header
    Entao é direcionado para a tela de opções da área logada

    Quando o usuário clicar na opção [Claro clube]
    Entao é direcionado para a tela de Claro Clube
    E preenche o campo [Numero do seu celular Claro] "11947725439"

    Quando o usuário clicar no botão [Continuar] Claro Clube
    Então é direcionado para a tela de opções de token

    Quando selecionar a opção [Receber código por e-mail] no e-mail "64475126aec442ce9ec10eb61a44bdd3@mailsac.com"
    Então é direcionado para a tela de token por email
    E preenche o campo [Digite o código recebido] com o token

    Quando o usuário clicar no botão [Confirmar]
    Então é direcionado para a tela de opções minha conta
    E será exibida a mensagem "O seu saldo Claro clube é de R$ 36"