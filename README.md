# Automação de Testes E-commerce
planoscelular.claro.com.br

Exemplo de comando para execução local dos testes:

mvn clean test "-Denv=S6" "-Dbrowser=chrome" "-Dheadless=false" "-Dmaximized=true" "-Dcucumber.filter.tags=@AquisicaoControleReprovClearSale"

Parâmetros:
    Ambiente            "-Denv=" [S1, S2, S4, S5, S6, D1, D2, D3 | default = S6]
    Browser             "-Dbrowser=" [chrome, firefox | default = chrome]
    Modo headless       "-Dheadless=" [true, false | default = true]
    Janela maximizada   "-Dmaximized=" [true, false | default = false]
    Cenário             "-Dcucumber.filter.tags=" [@tagParaExecução | sem valor default]