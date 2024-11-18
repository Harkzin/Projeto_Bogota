# language: pt
@Regressivo @Web

Funcionalidade: ECCMAUT-144 - Migração Pré para Pós Pago - Readequação Controle Fácil (Crédito Reprovado Passo 1)

  @Migracao @PrePos @CreditoReprovado @ControleFacil
  Cenario: Migração Pré para Pós Pago - Readequação Controle Fácil - Crédito Reprovado Passo 1
    
    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Pós] do header
    E o usuário clicar no botão [Eu quero!] do card do plano "17515" na PLP POS
    Entao é direcionado para a tela de Carrinho
      # Mas não deve haver alterações no valor e nem nas informações do Plano Pos
    E seleciona a opção [Migração], para o fluxo de troca de Plano
    E preenche os campos: [Telefone com DDD] "11947728801", [E-mail] e [CPF] "08039137888"
    Quando o usuário clicar no botão [Eu quero!] do Carrinho
    Então deve ser exibido um pop-up ofertando planos Controle Fácil no cartão de crédito
    E clicar no botão [Eu quero!] do pop-up
    Então é direcionado para a tela de Termos Combo



    # PARAMOS POR DEFEITO DE AMBIENTE



    # E o plano do carrinho será atualizado para o Plano Combo correspondente
    Então é direcionado para a tela de Termos Combo
    Então deve ser exibido um pop-up ofertando planos Controle Fácil no cartão de crédito
    Quando selecionar um dos planos Controle Fácil ofertados
    E clicar no botão [Eu quero!]
    Então é direcionado para a tela de Pagamento
    E o campo [Código enviado por SMS] deve estar disponível e editável
    E não deve haver alterações no valor e nem nas informações do Plano Controle Fácil
    Quando clicar no botão [Continuar]
    Então deve validar que o campo de token aceita somente 6 caracteres numéricos
    Quando preencher o token recebido
    E clicar no botão [Continuar]
    Então deve permanecer na tela de Pagamento
    E não deve haver alterações no valor e nem nas informações do Plano
    E os campos de cartão de crédito devem estar em branco e editáveis:
      | Número do Cartão de Crédito |
      | Venc (MM/AA)                |
      | CVV                         |
    Quando preencher os dados do cartão de crédito
    E validar as informações preenchidas
    E clicar no botão [Finalizar]
    Então é direcionado para a tela de Parabéns
    E deve exibir a mensagem "Parabéns {nome_cliente}! Sua solicitação para adquirir o plano 17GB foi recebida com sucesso! O número do seu pedido é {numero_pedido}. Você recebeu o número por SMS."
    E deve validar as seguintes informações do pedido:
      | Número do pedido      |
      | Número do Protocolo   |
      | CPF                   |
      | Plano                 |
      | Forma de Pagamento    |
      | Valor do Plano        |
    E não deve haver alterações no valor e nem nas informações do Plano
    E o campo [Endereço de Entrega] não deve conter informações preenchidas
