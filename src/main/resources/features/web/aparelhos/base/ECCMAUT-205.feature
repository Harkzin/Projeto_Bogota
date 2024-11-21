#language: pt

@Regressivo
@Web
Funcionalidade: ECCMAUT-205 Manter o Plano Pós Pago com fidelidade + Aparelho + Recusa multa de Aparelho

  @PosPago
  @Fidelidade
  @Aparelho
  @RecusaMulta
  Cenario: Manter Plano Pós-Pago com Fidelidade + Aparelho + Recusa da Multa de Aparelho

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Celulares] do header
    Então é direcionado para a tela PLP de Aparelho

    Quando o usuário clicar no botão [Eu quero!] do card do Aparelho "000000000000018006"
    #Valida que no componente “Sobre seu pedido” está previamente preenchido com a opção “Quero uma linha nova da Claro”
    Então é direcionado para a PDP do Aparelho selecionado
    E seleciona a opção [Manter meu número Claro], para o fluxo de Manter o Plano com fidelidade + Aparelho
    Então é exibido o popover para login
    E preenche o campo [Seu numero Claro] com "11945583804"

    Quando clicar no botão [Acessar] do popover
    Então é exibido as opções e informações para cliente Claro
    E seleciona a plataforma [Claro Pós-Pago]
    E seleciona o plano "17528"

    Quando o usuário clicar no botão [Comprar] da PDP do Aparelho
    Então é direcionado para a tela de Carrinho
    E preenche os campos: [E-mail] "cliente@exemplo.com" e [CPF] "49014340800"

    Quando o usuário clicar no botão [Continuar] do Carrinho
    Então é direcionado para a tela de Dados Pessoais
    E preenche os campos de dados pessoais: [Nome Completo] "ECCMAUT MIGRA POST", [Data de Nascimento] "20022000" e [Nome da Mãe] "Marta Silva"
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP402"

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Então é direcionado para a tela de Customizar Fatura
    E deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada

    Quando o usuário selecionar o método de recebimento da fatura [E-mail]
    Então não deve haver alterações no valor e nem nas informações do Plano

    Quando o usuário selecionar a forma de pagamento [Débito]
    Então o valor do Plano será atualizado no Resumo da compra para Débito
    E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    Então é direcionado para a tela de SMS
    E preenche o campo [Código enviado Por SMS] com o token recebido

    Quando o usuário clicar no botão [Finalizar] da tela de SMS
    Então será direcionado para a tela [Forma de Pagamento]
    E o usuário clicar no botão [Adicionar cartão de crédito]
    E será exibido o iframe de pagamento do cartão
    E preenche os dados do cartão: [Nome] "ECCMAUT MIGRA POST", [Número] "2223000250000004", [Data de validade] "0135", [CVV] "123" e [Parcelas] "2"

    Quando o usuário clicar no botão [Confirmar] do iframe do cartão da tela [Forma de Pagamento]
    Então é direcionado para a tela de Parabéns
    E os dados do pedido estão corretos

    Quando o usuário clicar no botão [Não Concordo] na tela de Multa Vigente
    Então é direcionado para a tela com a mensagem "Seu pedido de troca de aparelho não poderá ser concluído"
    E é exibido o texto de orientação: "Como adquirir um novo: 1. Adquira um novo aparelho mantendo o plano sem fidelidade, 2. Aguarde o término da fidelidade do seu contrato atual e então realize sua compra"

    Quando o usuário clicar no botão [Ok, entendi]
    Então é redirecionado para a página de Home da loja online
