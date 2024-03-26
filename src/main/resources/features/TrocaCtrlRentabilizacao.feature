#language: pt

@Regressivo
Funcionalidade: Troca Controle - Rentabilização - Débito

  @Troca
  @Controle
  @TrocaControleRentabilizacaoDebito
  Cenario: Troca Controle - Rentabilização - Débito
    Dado que o usuário acesse a URL parametrizada para a oferta de rentabilização ""
    E preenche os campos: [Telefone com DDD] "11999999988", [E-mail] "clordertest@mailsac.com" e [CPF] "74866436344"

    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então é direcionado para a tela de Customizar Fatura
    E não deve haver alterações no valor e nas informações do Plano
    E seleciona a forma de pagamento: "Débito"
    E marca o checkbox de termos de aceite

    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura
    Entao é direcionado para a tela de SMS
    E não deve haver alterações no valor e nas informações do Plano
    E preenche o campo [Código enviado Por SMS] com o token recebido

    Quando o usuário clicar no botão [Continuar] da tela de SMS
    Entao é direcionado para a tela de Parabéns
    E não deve haver alterações no valor e nas informações do Plano
    E os dados do pedido estão corretos