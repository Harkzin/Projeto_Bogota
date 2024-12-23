# language: pt
@Regressivo @Web

Funcionalidade: ECCMAUT-157 - Portabilidade Controle Fácil - Crédito Reprovado (Passo 3)  | CPF Score Zerado

  @Migracao @PrePago @Controle @ReadequacaoControleFacilEspelhado
  Cenario: Portabilidade Controle Fácil - Crédito Reprovado (Passo 3)

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Controle] do header
    E o usuário clicar no botão [Eu quero!] no card do plano "17528" da PLP
    Entao é direcionado para a tela de Carrinho


    E seleciona a opção [Portabilidade]
    E preenche os campos: [Telefone com DDD] "", [E-mail] e [CPF] ""
    E o usuário clicar no botão [Eu quero!] do Carrinho
    E preenche os campos de dados pessoais: [Nome Completo] "TESTES TESTES", [Data de Nascimento] "01022000" e [Nome da Mãe] "Marta Silva"
    Entao é direcionado para a tela de Dados Pessoais
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "12" e [Complemento] "CASA"
    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Entao é direcionado para a tela de Customizar Fatura

    E deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
    E deve ser exibido as datas de vencimento
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura - Termos
    E clicar no [Eu quero!] no Controle Fácil