# Automação de Testes E-commerce

planoscelular.claro.com.br

Exemplo de comando para execução local dos testes web:
mvn clean test "-Denv=S6" "-Dheadless=false" "-Dcucumber.filter.tags=@Regressivo"

Exemplo de comando para execução na browserstack:
export BUILD_NAME="RELEASE19.0.0" && mvn clean test "-Denv=S6" "-Dbrowserstack=true" "-Dcucumber.filter.tags=@MeusPedidosHeader"

Exemplo de comando para execução das apis:
mvn clean test "-Denv=S6" "-Dapi=true" "-Dcucumber.filter.tags=@Regressivo"

Parâmetros:
Ambiente            "-Denv=" [S1, S2, S4, S5, S6, D1, D2, D3 | default = S6]
BrowserStack        "-Dbrowserstack=" [true, false]
API                 "-Dapi=" [true, false | false ]
Modo headless       "-Dheadless=" [true, false | default = false (true executa em modo minimizado, é necessário no Jenkins)]
Cenário             "-Dcucumber.filter.tags=" [@tagParaExecução | sem valor default]