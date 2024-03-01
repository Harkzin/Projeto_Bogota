#language: pt

@regressivo
Funcionalidade: Validar Meus Pedidos Header

  @home
  @validaMeusPedidos
  Cenario: Validar Meus Pedidos Header
    Dado que acesso a Loja Online
    E clicar no botão "Entrar"
    E preencher o campo “Seu telefone Claro” com o msidn "11947190768"
    E clicar no botão "Acessar"
#    E validar que o botão Entrar foi alterado para o "11947190768"
    E clicar no botão "11947190768"
    E clicar no botão "Meus pedidos"
    E preencho com o Token
    E validar que foi direcionado para a página de Meus Pedidos
    E validar que foi exibido o Número do pedido, Data do pedido e o Status do(s) pedido(s) já realizado(s) anteriormente
    E acessar o pedido mais recente, clicando no Número do pedido dele
    Entao validar que foi direcionado para a página de acompanhamento de pedido
