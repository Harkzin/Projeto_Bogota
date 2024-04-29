#language: pt

@Regressivo
Funcionalidade: Planos - Gross

  @Aquisicao
  @Controle
  @AquisicaoControleReprovClearSale
  Cenario: Aquisicao Controle - Reprovacao Clear Sale
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17218" do carrossel da Home
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E seleciona a opção [Aquisição]
    E preenche os campos: [Celular de contato] "11999999988", [E-mail] e [CPF] [CPF aprovado na clearSale? "false", CPF na diretrix? "false"]

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E preenche os campos de dados pessoais: [Nome Completo] "ECOMMAUT AQSCTRL REPROVCLEAR", [Data de Nascimento] "01011991" e [Nome da Mãe] "NOME MAE"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP202"
      E deve ser exibido os tipos de entrega

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então é direcionado para a tela de Customizar Fatura
    Mas não deve haver alterações no valor e nem nas informações do Plano
      E deve ser exibido as opções de pagamento
      E deve ser exibido os meios de recebimento da fatura
      E deve ser exibido as datas de vencimento
    E seleciona a forma de pagamento: "Débito"
    E preenche os dados bancários
    E seleciona o método de recebimento da fatura: "WhatsApp"
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Então é direcionado para a tela de Parabéns
      Mas não deve haver alterações no valor e nem nas informações do Plano
    E os dados do pedido estão corretos


