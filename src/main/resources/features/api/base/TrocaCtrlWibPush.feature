#language: pt

@Desativado
Funcionalidade: ECCMAUT-927 - Troca Ctrl WibPush

  #@Api
  @ApiTrocaCtrlWibPush
  Cenario: Troca WibPush
    * authorizationserver-oauth-token
    * create-order-wibpush [msisdn "11940665042"], [plan "17266"] e [promotionCode "37811"]