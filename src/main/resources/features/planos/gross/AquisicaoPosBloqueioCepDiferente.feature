#language: pt

@Desativado
Funcionalidade: Planos - Gross

  @Aquisicao
  @Pos
  @AquisicaoPosBloqueioCepDiferente
  Cenario: Cenário de erro - Aquisição Pós - Bloqueio para CEP diferente da regionalização
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17218" do carrossel da Home
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Aquisição]
    E preenche os campos: [Celular de contato] "11999999988", [E-mail] e [CPF] [CPF aprovado na clearSale? "true", CPF na diretrix? "false"]

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "ECOMMAUT AQSBLOQUEIOCEP", [Data de Nascimento] "01011991" e [Nome da Mãe] "Marta Silva"
    E preenche os campos de endereço: [CEP] convencional "20010010", [Número] "65" e [Complemento] "AP202"
      E deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então será recarregada a página e exibida a mensagem de erro: "O CEP deve ser do mesmo estado (UF) do DDD escolhido"
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "70" e [Complemento] ""
      E deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então é direcionado para a tela de Customizar Fatura
      Mas não deve haver alterações no valor e nem nas informações do Plano