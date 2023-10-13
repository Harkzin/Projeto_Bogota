#language: pt

@regressivo
Funcionalidade: Aquisicao Pos - Bloqueio Cliente Dependente

  @pos
  @aquisicao
  @bloqueioCEPdiferente
  Cenario: Bloqueio cliente Dependente
    Dado que acesso a Loja Online
    E selecionar o "3" plano do carrossel da Home clicando no botão Eu quero! dele
    E validar que não há alterações no valor e/ou informações do Plano
    E preencho os campos ddd "11", telefone "11947438023", email "eutesteauto@outlook.com" e cpf "50357581091"
    E clicar no botão "Eu quero!"
    E preencho os campos Nome Completo "AQS CONVENCIONAL", Data De Nascimento "09/10/1980" e Nome da Mãe "Marta Silva"
    E preencho os campos "50030030", "65" e "apt 81" no endereço
    Quando clicar no botão "Continuar"
    Então validar que foi exibida uma mensagem de erro do cep "O CEP deve ser do mesmo estado (UF) do DDD escolhido"

