#language: pt

@regressivo
Funcionalidade: Validar Migracao cliente THAB no Backoffice

  @backoffice
  @BackofficeMigracaoTHAB
  Cenario: "Validar Migracao cliente THAB no Backoffice"
    Dado que ao acessar o backoffice
    Quando preencher "pedidos" no filtro
    E selecionar a opcao "Pedidos"
    E selecionar o cliente "Ecomm Pre Bitencourt" na página de pedidos
    E selecionar a aba "Pagamento e entrega"
    E validar o msisdn "11947489030"
    E validar que o campo "Modalidade" apresenta o valor "Migração"
    Entao validar que o campo "Status do pedido" apresenta o valor "Ativacao migracao concluida"
