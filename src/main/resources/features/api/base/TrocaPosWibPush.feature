#language: pt

@Regressivo
Funcionalidade: ECCMAUT-926 - Troca Pos WibPush

  @API
  @ApiTrocaPosWibPush
  Cenario: Troca WibPush
    * authorizationserver-oauth-token
    * create-order-wibpush [msisdn "11940665042"], [plan "17558"] e [promotionCode "37811"]