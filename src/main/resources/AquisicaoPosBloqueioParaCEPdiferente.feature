#language: pt

@regressivo
Funcionalidade: Aquisicao pos

  @aquisicao
  @planoPos
  @bloqueioCEPdiferente
  Cenario: Bloqueio cliente Dependente
    Dado que acesso a Loja Online
    E selecionar um Plano Controle do carrossel da Home clicando no botão "Eu quero!" dele
    E validar que não há alterações no valor e/ou informações do Plano
    E selecionar a opção "Quero uma linha nova da Claro"
    E preencho os campos ddd "11", telefone "11947438023", email "eutesteauto@outlook.com" e cpf "50357581091" no fluxo de "aquisicao"
    Quando clicar no botão "Eu quero!"
    E preencher os dados pessoais: "AQS CONVENCIONAL", "09/10/1980", "Marta Silva"
#    E aguardar o acesso a secao de "dados de endereco" do pedido
    E preencho os campos "50030030", "65" e "apt 81" no endereço
    Quando clicar no botão "Continuar"
    Então validar que foi exibida uma mensagem de erro do cep "O CEP deve ser do mesmo estado (UF) do DDD escolhido"

