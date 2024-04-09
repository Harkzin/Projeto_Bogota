#language: pt

@Regressivo
Funcionalidade: Gross - Aquisição - Planos

  @Aquisicao
  @Controle
  @AquisicaoControleExpressaBoleto
  Cenario: Aquisição Controle - Entrega expressa - Boleto
    Dado que o usuário acesse a Loja Online
    Quando selecionar o plano de id "17218" do carrossel da Home
    Então é direcionado para a tela de Carrinho
      Mas não deve haver alterações no valor e nas informações do Plano
    E seleciona o fluxo "Aquisição"
    E preenche os campos: [Celular de contato] "11999999988", [E-mail] "clordertest@mailsac.com" e [CPF] [CPF aprovado na clearSale? "true", CPF na diretrix? "false"]

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
      Mas não deve haver alterações no valor e nas informações do Plano
    E preenche os campos de informações pessoais: Nome Completo "ECOMMAUT AQSCTRL EXPRESBOL", Data De Nascimento "01011991" e Nome da Mãe "NOME MAE"
    E preenche os campos de endereço: [CEP] com um CEP de entrega "expressa", [Número] "65" e [Complemento] "AP202"

    Quando o usuário clicar no botão Continuar da tela de Dados Pessoais
    Então é direcionado para a tela de Customizar Fatura
      Mas não deve haver alterações no valor e nas informações do Plano
      E deve ser exibido as opções de pagamento
      E deve ser exibido os meios de recebimento da fatura
      E deve ser exibido as datas de vencimento
    E seleciona a forma de pagamento: "Boleto"
    E seleciona o método de recebimento da fatura: "E-mail"
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Entao é direcionado para a tela de Parabéns
      Mas não deve haver alterações no valor e nas informações do Plano
    E os dados do pedido estão corretos