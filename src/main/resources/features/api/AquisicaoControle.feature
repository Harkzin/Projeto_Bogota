#language: pt



@Regressivo
Funcionalidade: Aquisicao Controle

  @API
  @ApiAquisicaoControle
  Cenario: Aquisicao Controle
    * authorizationserver-oauth-token
    * cart-new
    * add-offer-plan [fields "/17266?fields=FULL&paymentMethod=debitcard&loyalty=false&invoiceType=DIGITAL&state=BR-SP&city=sao_paulo-SP"]
    * identificar-cliente [msisdn "11953662539"], [cpf "43544888840"], [ddd "11"] [service "NEWLINE"]