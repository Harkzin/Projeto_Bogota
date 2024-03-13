# Automação de Testes E-commerce Planos
planoscelular.claro.com.br


Exemplo de comandos para execução dos testes:

Teste no navegador default.
mvn clean test "-Denv=S6" "-Dcucumber.filter.tags=@bloqueioCEPdiferente"

Teste com navegador informado e modo headless desativado.
mvn clean test "-Denv=S6" "-Dcucumber.filter.tags=@bloqueioCEPdiferente" "-Dbrowser=chrome" "-Dheadless=false"

Parâmetros opcionais:
    Ambiente        "-Denv=S6" [S1, S2, S4, S5, S6, D1, D2, D3 | default = S6]
    Browser         "-Dbrowser=chrome" [chrome, firefox | default = chrome]
    Modo Headless   "-Dheadless=false" [true, false | default = true]