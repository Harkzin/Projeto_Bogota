#language: pt

@Regressivo
Funcionalidade: Planos - Gross

  @Aquisicao
  @Pos
  @AquisicaoPosBloqueioCepDiferente
  Cenario: Cenário de erro - Aquisição Pós - Bloqueio para CEP diferente da regionalização
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17218" do carrossel da Home
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona o fluxo "Aquisição"
    E preenche os campos: [Celular de contato] "11999999988", [E-mail] "clordertest@mailsac.com" e [CPF] [CPF aprovado na clearSale? "true", CPF na diretrix? "false"]

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de informações pessoais: Nome Completo "ECOMMAUT AQSBLOQUEIOCEP", Data De Nascimento "01011991" e Nome da Mãe "Marta Silva"
    E preenche os campos de endereço: [CEP] "21060070", [Número] "65" e [Complemento] "AP202"
      E deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então será exibida a mensagem de erro e não será possível continuar: "O CEP deve ser do mesmo estado (UF) do DDD escolhido"