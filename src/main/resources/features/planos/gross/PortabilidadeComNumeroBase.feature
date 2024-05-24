#language: pt

@Regressivo
Funcionalidade: Planos - Gross

  @Portabilidade
  @Pos
  @PortabilidadeComNumeroBase
  Cenario: Portabilidade Pós - Entrega Expressa (Mesmo endereço)
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17270" do carrossel da Home
    Então é direcionado para a tela de Carrinho
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Portabilidade]
    E preenche os campos: [Telefone a ser portado com DDD] "11913572571", [E-mail] e [CPF] [CPF aprovado na clearSale? "true", CPF na diretrix? "false"]

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então será exibida a mensagem de erro numero base