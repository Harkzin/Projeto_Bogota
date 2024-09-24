package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.DependentesPage;
import web.models.CartOrder;


public class DependentesSteps {

    private final DependentesPage dependentesPage;
    private final CartOrder cartOrder;

    @Autowired
    public DependentesSteps(DependentesPage dependentesPage, CartOrder cartOrder) {
        this.dependentesPage = dependentesPage;
        this.cartOrder = cartOrder;
    }

    @Então("é direcionado para a tela de Dependentes")
    public void validarPaginaDadosPessoais() {
        dependentesPage.validarPaginaDependentes();
    }

    @Quando("o usuário clicar no botão [Seguir sem dependentes]")
    public void clicarSeguirSemDependentes() {
        dependentesPage.clicarSeguirSemDependentes();
    }

    @E("adiciona o primeiro dependente, com numero {string}")
    public void adicionarPrimeiroDependentePort(String numero) {
        dependentesPage.clicarAdicionarDependente(1);
        dependentesPage.inserirNumeroDependentes( numero);
        dependentesPage.clicarConfirmarDependente();
    }

    @E("adiciona o segundo dependente, com numero {string}")
    public void adicionarSegundoDependentePort(String numero) {
        dependentesPage.clicarAdicionarOutroDependente(2);
        dependentesPage.inserirNumeroDependentes(numero);
        dependentesPage.clicarConfirmarDependente();
    }

    @E("adiciona o terceiro dependente, com numero {string}")
    public void adicionarTerceiroDependentePort(String numero) {
        dependentesPage.clicarAdicionarOutroDependente(3);
        dependentesPage.inserirNumeroDependentes(numero);
        dependentesPage.clicarConfirmarDependente();
    }

    @E("adiciona o primeiro dependente, com número novo")
    public void adicionarPrimeiroDependente() {
        cartOrder.addNewLineDependent("DEP1");

        dependentesPage.clicarAdicionarDependente(1);
        dependentesPage.adicionarNovoNumeroDependente();
        dependentesPage.clicarConfirmarDependente();
    }

    @E("adiciona o segundo dependente, com número novo")
    public void adicionarSegundoDependente() {
        cartOrder.addNewLineDependent("DEP2");

        dependentesPage.clicarAdicionarOutroDependente(2);
        dependentesPage.adicionarNovoNumeroDependente();
        dependentesPage.clicarConfirmarDependente();
    }

    @E("adiciona o terceiro dependente, com número novo")
    public void adicionarTerceiroDependente() {
        cartOrder.addNewLineDependent("DEP3");

        dependentesPage.clicarAdicionarOutroDependente(3);
        dependentesPage.adicionarNovoNumeroDependente();
        dependentesPage.clicarConfirmarDependente();
    }

    @E("o usuário clicar no botão [Continuar] na tela de Dependentes")
    public void clicarContinuar() {
        dependentesPage.clicarContinuar();
    }
}
