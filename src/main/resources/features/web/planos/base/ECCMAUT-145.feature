# language: pt
@Regressivo @Web

Funcionalidade: ECCMAUT-145 - Migração Pré para Controle - Readequação Controle Fácil (Crédito Reprovado Passo 3)

  @Migracao @PrePago @ReadequacaoControleFacil
  Cenario: Migração Pré para Controle - Readequação Controle Fácil (Crédito Reprovado Passo 3)

    Dado que o usuário acesse a Loja Online
    Quando o usuário clicar na opção [Controle] do header
    E o usuário clicar no botão [Eu quero!] do card do plano "17528" na PLP Controle
    Entao é direcionado para a tela de Carrinho

      # Mas não deve haver alterações no valor e nem nas informações do Plano Controle
      
    Quando seleciona a opção [Migração], para o fluxo de troca de Plano
    E preenche os campos: [Telefone com DDD] "11947728801", [E-mail] e [CPF] "08039137888"
    E o usuário clicar no botão [Eu quero!] do Carrinho
    Entao é direcionado para a tela de Dados Pessoais
    E preenche os campos de endereço: [CEP] convencional "01001001", [Número] "65" e [Complemento] "AP402"

    Quando o usuário clicar no botão [Continuar] da tela de Dados Pessoais
    Entao é direcionado para a tela de Customizar Fatura

      # Mas não deve haver alterações no valor e nem nas informações do Plano Controle

    E deve ser exibido as opções de pagamento, com a opção [Débito] selecionada
    E o usuário clicar no botão [Boleto] da pagina Costumizar Fatura
    E deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada
    E o usuário clicar no botão [Débito] da pagina Costumizar Fatura
      # E deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada
      #   Quando o usuário selecionar o método de recebimento da fatura [WhatsApp]
    Quando preenche os dados bancários
    E deve ser exibido as datas de vencimento
    E marca o checkbox de termos de aceite
    
    Quando o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos
    




    # Então é direcionado para a página de planos Pré-Pago (PLP de planos Pré)

    # Quando o usuário seleciona o primeiro plano Pré na prateleira
    # E clica no botão “Eu quero!”
    # Então é direcionado para a página de Carrinho
    # E valida que no Resumo da Compra, o nome e valor do plano não foram alterados 

    # E valida a existência de opções de seleção com rádio buttons desmarcados:
    #   | Mudar meu plano da Claro      |
    #   | Trazer meu número para Claro   |
    #   | Quero uma linha nova da Claro  |

    # Quando o usuário seleciona o rádio button “Mudar meu plano da Claro”
    # Então valida que foram abertos 3 novos campos:
    #   | Telefone com DDD |
    #   | E-mail           |
    #   | CPF              |

    # E valida que os campos “Telefone com DDD, E-mail e CPF” são editáveis
    # E preenche os campos com dados válidos:
    #   | Telefone com DDD | 11947725638 |
    #   | E-mail           | usuario@exemplo.com |
    #   | CPF              | 19398965178 |

    # Quando o usuário clica no botão “Eu quero!”
    # Então é direcionado para a página de Endereço
    # E valida que no Resumo da Compra, o nome e valor do plano não foram alterados
    # E valida a abertura do campo “CEP” editável e em branco
    # E preenche o campo “CEP” com um valor válido

    # Quando o campo CEP é preenchido
    # Então valida que são abertos os campos:
    #   | Endereço    |
    #   | Número      |
    #   | Complemento |
    #   | Bairro      |
    #   | UF          |
    #   | Cidade      |

    # E valida que os campos "Endereço, Bairro, UF e Cidade" são preenchidos automaticamente
    # E valida que o campo "Número" está em branco e editável
    # E valida que o campo "Complemento" está em branco e editável
    # E preenche os campos “Número e Complemento” conforme especificado

    # Quando o usuário clica no botão “Continuar”
    # Então é direcionado para a página de Forma de Pagamento e Recebimento de Fatura
    # E valida que no Resumo da Compra, o nome e valor do plano não foram alterados
    # E valida que o radio button de termos e contratos está desmarcado

    # E valida as combinações de pagamento e recebimento de fatura:
    #   | Forma de Pagamento | Recebimento    |
    #   | DCC                | WhatsApp       |
    #   | DCC                | E-mail         |
    #   | DCC                | Correios       |
    #   | Boleto             | WhatsApp       |
    #   | Boleto             | E-mail         |
    #   | Boleto             | Correios       |

    # Quando o usuário seleciona “DCC + WhatsApp” para forma de pagamento e recebimento
    # E preenche os campos [Banco, Agência e Conta] com dados válidos
    # E marca o radio button de concordância com os termos regulatórios
    # E clica em “Continuar”

    # Então é direcionado para a página do Produto Controle Fácil

    # Quando o usuário seleciona o plano Controle Fácil (Cartão de Crédito) e clica em “Eu quero”
    # Então é direcionado para a página de “Pagamento”
    # E valida que o nome e valor do plano foram atualizados para o Controle Fácil selecionado
    # E valida a abertura do campo “Código enviado por SMS” em branco e editável
    # E preenche o campo “Código enviado por SMS” com um código válido de 6 caracteres

    # Quando o usuário clica em “Continuar”
    # Então permanece na página de “Pagamento”
    # E valida a abertura do Iframe da “Bemobi”
    # E valida os campos no Iframe para:
    #   | Campo                   | Limite |
    #   | Número do cartão        | 16 dígitos |
    #   | Validade do cartão      | 4 dígitos no formato MM/AA |
    #   | CVV                     | 3 dígitos |

    # Quando o usuário preenche os campos do Iframe e clica em “Finalizar”
    # Então é exibida uma mensagem de erro informEo que o crédito foi reprovado
    # E valida que o usuário permanece na página de “Pagamento”
    # E valida que o campo de informações para novo cartão de crédito é aberto novamente

    # Quando o usuário optar por sair da página de pagamento sem inserir um novo cartão
    # Então é direcionado para a página de resumo com a mensagem:
    #   """
    #   Sua solicitação para adquirir o <nome do plano> foi recusada por falta de crédito aprovado. Tente novamente com outro cartão ou contate nosso suporte.
    #   """

    # E valida que os dados do pedido são exibidos:
    #   | Número do Pedido |
    #   | CPF              |
    #   | Plano            |
    #   | Forma de Pagamento |
