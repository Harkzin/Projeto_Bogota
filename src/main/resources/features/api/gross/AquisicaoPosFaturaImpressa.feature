#language: pt

@Api
Funcionalidade: ECCMAUT-925 - Aquisicao Pos Fatura Impressa

  @ApiAquisicaoPosFaturaImpressa
  Cenario: Aquisicao Pos Fatura Impressa
    * authorizationserver-oauth-token
    * cart-new
    * add-offer-plan [plan "17515"], [fields "?fields=FULL&paymentMethod=debitcard&loyalty=false&invoiceType=DIGITAL&state=BR-SP&city=sao_paulo-SP"]
    * identificar-cliente [msisdn "11988887777"], [CPF aprovado na clearSale? "true", CPF na diretrix? "false"], [ddd "11"] e [service "NEWLINE"]
    * personal-info [fullName "NOME CENARIO"]
    * get-address [cep "01001001"]
    * save-address
    * get-payments
    * save-payments [invoiceType "PRINTED"] e [paymentMode "debitcard"]
    * validate-credit
    * create-order