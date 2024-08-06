#language: pt

@RegressivoConsulta
Funcionalidade: ECCMAUT-510 - Portabilidade - Numero Base

  @Portabilidade
  @Pos
  @PortabilidadeComNumeroBase
  Cenario: Portabilidade - Numero Base
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17270" do carrossel da Home
    Então é direcionado para a tela de Carrinho
    Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Portabilidade]
    E preenche os campos: [Telefone a ser portado com DDD] "11913572571", [E-mail] e [CPF] [CPF aprovado na clearSale? "true", CPF na diretrix? "false"]

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então será exibida a mensagem de erro: "O número de telefone informado já consta em nossa base da Claro. Caso queira trocar seu plano, selecione"