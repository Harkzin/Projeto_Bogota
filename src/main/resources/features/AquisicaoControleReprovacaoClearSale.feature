#language: pt

@regressivo
Funcionalidade: Aquisicao Controle - Reprovacao Clear Sale

  @controle
  @aquisicao
  @entregaConvencional
  @boleto
  @reprovacaoClearSale
  Cenario: Aquisicao Controle - Reprovacao Clear Sale
    Dado que acesso a Loja Online
    E selecionar o "3" plano do carrossel da Home clicando no botão Eu quero! dele
#    E validar que não há alterações no valor e/ou informações do Plano
    E preencho os campos ddd "11", telefone "11992019933", email "eutesteauto@outlook.com" e cpf "61728554845"
    E clicar no botão "Eu quero!"
    E preencho os campos Nome Completo "AQS CONVENCIONAL", Data De Nascimento "09/10/1980" e Nome da Mãe "Marta Silva"
    E preencho os campos "01001001", "65" e "apt 81" no endereço
    E clicar no botão "Continuar"
    E seleciono a forma de pagamento
    E marco o checkbox de termos de aceite
    Quando clicar no botão "Continuar pagamento"
    Entao validar que não há alterações no valor e/ou informações do Plano na tela de parabens


