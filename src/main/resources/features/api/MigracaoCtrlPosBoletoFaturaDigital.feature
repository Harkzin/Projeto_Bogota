#language: pt



@Regressivo
Funcionalidade: Migracao Ctrl Pos Boleto Fatura Digital

  @API
  @ApiMigracaoCtrlPosBoletoFaturaDigital
  Cenario: Migracao Ctrl Pos Boleto Fatura Digital
    * authorizationserver-oauth-token
    * cart-new
    * add-offer-plan [plan "17270"], [fields "?fields=FULL&paymentMethod=debitcard&loyalty=false&invoiceType=DIGITAL&state=BR-SP&city=sao_paulo-SP"]
    * identificar-cliente [msisdn "11940664333"], [CPF "42811814744"], [ddd "11"] e [service "MIGRATE"]
    * personal-info [fullName "NOME CENARIO"]
    * get-address [cep "01001001"]
    * save-address
    * get-payments
    * save-payments [invoiceType "DIGITAL"] e [paymentMode "ticket"]
    * validate-credit
    * get-otp-token
    * send-otp-token
    * create-order