#language: pt

@Regressivo
Funcionalidade: Aquisicao de acessorio com 1 produto - Parcelamento

  #ECCMAUT-1092
  #mvn clean test "-Denv=S6" "-Dbrowser=chrome" "-Dheadless=false" "-Dmaximized=true" "-Dcucumber.filter.tags=@AquisicaoAcessProdParcel"

  @Aquisicao
  @Acessorio
  @AquisicaoAcessProdParcel
  Cenario: Aquisicao de acessorio com 1 produto - Parcelamento

    Dado que o usuário acesse a Loja Online

    Quando o usuário clicar na opção [Acessórios] do header
    Então é direcionado para a tela PLP de Acessorios
      E validar que [Todas as Ofertas] está selecionado

    Quando o usuário clicar no botão [Comprar!] no produto [Repetidor Mesh Deco]
    Então é direcionado para a PDP do Acessório selecionado

    Quando o usuário clicar no botão [Comprar!] na PDP de Acessórios
    Entao é direcionado para a tela de Carrinho de Acessórios

