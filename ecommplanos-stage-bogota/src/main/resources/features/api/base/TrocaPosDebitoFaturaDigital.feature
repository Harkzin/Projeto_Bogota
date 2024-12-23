#language: pt

@Api
Funcionalidade: ECCMAUT-933 - Troca Pos Debito Fatura Digital

  @ApiTrocaCtrlDebitoFaturaDigital
  Cenario: Troca Pos Debito Fatura Digital
    * authorizationserver-oauth-token
    * cart-new
    * add-offer-plan [plan "17515"], [fields "?fields=FULL&paymentMethod=debitcard&loyalty=false&invoiceType=DIGITAL&state=BR-SP&city=sao_paulo-SP"]
    * identificar-cliente [msisdn "11940664333"], [CPF "42811814744"], [ddd "11"] e [service "MIGRATE"]
    * personal-info [fullName "NOME CENARIO"]
    * get-address [cep "01001001"]
    * save-address
    * get-payments
    * save-payments [invoiceType "DIGITAL"] e [paymentMode "debitcard"]
    * validate-credit
    * get-otp-token
    * send-otp-token
    * create-order