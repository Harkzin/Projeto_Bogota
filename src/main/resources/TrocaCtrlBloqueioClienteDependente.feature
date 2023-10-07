#language: pt

@regressivo
Funcionalidade: Troca Controle - Bloqueio Cliente Dependente

  @troca
  @controle
  @bloqueioClienteDependente
  Cenario: Bloqueio cliente Dependente
    Dado que acesso a Loja Online
    E selecionar um Plano Controle do carrossel da Home clicando no botão "Eu quero!" dele
    E validar que não há alterações no valor e/ou informações do Plano
    E selecionar a opção "Mudar meu plano da Claro"
    E preencho os campos ddd "", telefone "11947438023", email "eutesteauto@outlook.com" e cpf "50357581091" no fluxo de "migracao"
    Quando clicar no botão "Eu quero!"
    Então validar que foi exibida uma mensagem de erro "Verificamos que a linha informada é um dependente. Somente o titular pode realizar as alterações no plano."