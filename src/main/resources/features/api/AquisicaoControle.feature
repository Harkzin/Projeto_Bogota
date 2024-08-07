#language: pt



@Regressivo
Funcionalidade: Aquisicao Controle Boleto

  @API
  @ApiAquisicaoControleBoleto
  Cenario: Aquisicao Controle Boleto
    * authorizationserver-oauth-token
    * cart-new
    * add-offer-plan [fields "/17218?fields=FULL&paymentMethod=debitcard&loyalty=false&invoiceType=DIGITAL&state=BR-SP&city=sao_paulo-SP"]
    * identificar-cliente [msisdn "11988887777"], [CPF aprovado na clearSale? "true", CPF na diretrix? "false"], [ddd "11"] e [service "NEWLINE"]
    * personal-info [fullName "NOME CENARIO"]
    * get-address [cep "03662010"]
    * save-address
    * get-payments
    * save-payments [invoiceType "DIGITAL"] e [paymentMode "ticket"]
    * validate-credit
    * create-order