#language: pt
#author: Danilo dos Santos


@regressivo
Funcionalidade: Troca Controle - Recusa multa

  @troca
  @controle
  @TrocaCtrlRecusaMulta
  Cenario: CT-<ct> Troca Controle Controle - <tipoTroca> Base PDP
    Dado que acesso a Loja Online
    E selecionar um Plano Controle do carrossel da Home clicando no botão "Eu quero!" dele
    E validar que não há alterações no valor e/ou informações do Plano
    E selecionar a opção "Mudar meu plano da Claro"
    E preencho os campos ddd "", telefone "11947431002", email "claroteste61@gmail.com" e cpf "64526647861" no fluxo de "migracao"
    Quando clicar no botão "Eu quero!"
    E clicar "Não concordo"
    Quando clicar no checkbox "Ok, entendi"
    Então  validar que foi direcionado para a Home





