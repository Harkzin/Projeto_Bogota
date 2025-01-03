#language: pt

@Web
Funcionalidade: ECCMAUT-156 - Aquisição Controle + Readequação Controle Fácil - Crédito Reprovado

  @Aquisicao
  @Controle
  @AquisicaoCtrlCreditoReprovado
  Cenario: Aquisição Controle + Readequação Controle Fácil - Crédito Reprovado
      Dado que o usuário acesse a Loja Online
      Quando o usuário clicar na opção [Controle] do header
      Então é direcionado para a PLP Controle
      Quando o usuário clicar no botão [Eu quero!] no card do plano "17536" da PLP
      Entao é direcionado para a tela de Carrinho
      Então é direcionado para a tela de Dados Pessoais
      E preenche os campos de dados pessoais: [Nome Completo] "ECOMMAUT AQSCTRL REPROVCLEAR", [Data de Nascimento] "01011991" e [Nome da Mãe] "NOME MAE"
      E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP202"
      E deve ser exibido os tipos de entrega
      