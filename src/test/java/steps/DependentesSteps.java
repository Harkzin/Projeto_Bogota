package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import org.springframework.beans.factory.annotation.Autowired;
import pages.DependentesPage;
import support.CartOrder;


public class DependentesSteps {

    private final DependentesPage dependentesPage;
    private final CartOrder cartOrder;
    private int numeroDependente;

    @Autowired
    public DependentesSteps(DependentesPage dependentesPage, CartOrder cartOrder) {
        this.dependentesPage = dependentesPage;
        this.cartOrder = cartOrder;
    }

    @Então("é direcionado para a tela de Dependentes")
    public void validarPaginaDadosPessoais() {
        dependentesPage.validarPaginaDependentes();
    }

    @E("o usuário adiciona um dependente de numero {string}")
    public void preencherDadosPessoais(String numero) {
        //dependentesPage.clicarAdicionarDependente();
        dependentesPage.inserirNumeroDependentes(numero);
        dependentesPage.clicarConfirmarDependente();
    }

    @E("adiciona o primeiro dependente, com número novo")
    public void adicionarPrimeiroDependente() {
        numeroDependente = 1;
        dependentesPage.clicarAdicionarDependente(numeroDependente);
        dependentesPage.adicionarNovoNumeroDependente();
        dependentesPage.clicarConfirmarDependente();

    }

    @E("adiciona o segundo dependente, com número novo")
    public void adicionarSegundoDependente() {
        numeroDependente = 2;
        dependentesPage.clicarAdicionarOutroDependente(numeroDependente);
        dependentesPage.adicionarNovoNumeroSegundoDependente();
        dependentesPage.clicarConfirmarSegundoDependente();
    }

    @E("o usuário clicar no botão [Continuar] na tela de Dependentes")
    public void clicarContinuar() {
        dependentesPage.clicarContinuar();
    }
}
