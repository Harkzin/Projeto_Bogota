#language: pt

@regressivo
Funcionalidade: Aquisicao Pós - Bloqueio para CEP diferente da regionalização

  @pos
  @aquisicao
  @bloqueioCEPdiferente
  Cenario: Bloqueio para CEP diferente da regionalização
    Dado que acesso a Loja Online
    E selecionar o "3" plano do carrossel da Home clicando no botão Eu quero! dele
    E validar que não há alterações no valor e nas informações do Plano
    E preencho os campos Celular de contato "11999999988", E-mail "eutesteauto@outlook.com" e CPF [CPF aprovado na clearSale? "true", CPF na diretrix? "false"]
    E clicar no botão "Eu quero!"
    E preencho os campos Nome Completo "AQS CONVENCIONAL", Data De Nascimento "09/10/1980" e Nome da Mãe "Marta Silva"
    E preencho os campos "50030030", "65" e "apt 81" no endereço
    Quando clicar no botão "Continuar"
    Então validar que foi exibida uma mensagem de erro do cep "O CEP deve ser do mesmo estado (UF) do DDD escolhido"