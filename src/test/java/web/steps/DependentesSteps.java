package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import massasController.ConsultaCPFMSISDN;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.DependentesPage;
import web.models.CartOrder;


public class DependentesSteps {

    private final DependentesPage dependentesPage;
    private final CartOrder cart;

    @Autowired
    public DependentesSteps(DependentesPage dependentesPage, CartOrder cart) {
        this.dependentesPage = dependentesPage;
        this.cart = cart;
    }

    @Então("é direcionado para a tela de Dependentes")
    public void validarPaginaDadosPessoais() {
        dependentesPage.validarPaginaDependentes();
    }

    @Quando("o usuário clicar no botão [Seguir sem dependentes]")
    public void clicarSeguirSemDependentes() {
        dependentesPage.clicarSeguirSemDependentes();
    }

    @E("adiciona o primeiro dependente, com numero de portabilidade")
    public void adicionarPrimeiroDependentePort() {
        dependentesPage.clicarAdicionarDependente(1);
        String numeroPortabilidadeCompleto = ConsultaCPFMSISDN.consultarDadosPortabilidade();
        String numeroPortabilidadeSemDDD = numeroPortabilidadeCompleto.substring(2);
        dependentesPage.inserirNumeroDependentes(numeroPortabilidadeSemDDD);
        dependentesPage.clicarConfirmarDependente();
    }

    @E("adiciona o segundo dependente, com numero de portabilidade")
    public void adicionarSegundoDependentePort(String numero) {
        cart.addPortabilityDependent("DEP2", numero);

        dependentesPage.clicarAdicionarOutroDependente(2);
        String numeroPortabilidadeCompleto = ConsultaCPFMSISDN.consultarDadosPortabilidade();
        String numeroPortabilidadeSemDDD = numeroPortabilidadeCompleto.substring(2);
        dependentesPage.inserirNumeroDependentes(numeroPortabilidadeSemDDD);
        dependentesPage.clicarConfirmarDependente();
    }

    @E("adiciona o terceiro dependente, com numero de portabilidade")
    public void adicionarTerceiroDependentePort() {
        dependentesPage.clicarAdicionarOutroDependente(3);
        String numeroPortabilidadeCompleto = ConsultaCPFMSISDN.consultarDadosPortabilidade();
        String numeroPortabilidadeSemDDD = numeroPortabilidadeCompleto.substring(2);
        dependentesPage.inserirNumeroDependentes(numeroPortabilidadeSemDDD);
        dependentesPage.clicarConfirmarDependente();
    }

    @E("adiciona o primeiro dependente, com número novo")
    public void adicionarPrimeiroDependente() {
        cart.addNewLineDependent("DEP1");

        dependentesPage.clicarAdicionarDependente(1);
        dependentesPage.adicionarNovoNumeroDependente();
        dependentesPage.clicarConfirmarDependente();
    }

    @E("adiciona o segundo dependente, com número novo")
    public void adicionarSegundoDependente() {
        cart.addNewLineDependent("DEP2");

        dependentesPage.clicarAdicionarOutroDependente(2);
        dependentesPage.adicionarNovoNumeroDependente();
        dependentesPage.clicarConfirmarDependente();
    }

    @E("adiciona o terceiro dependente, com número novo")
    public void adicionarTerceiroDependente() {
        cart.addNewLineDependent("DEP3");

        dependentesPage.clicarAdicionarOutroDependente(3);
        dependentesPage.adicionarNovoNumeroDependente();
        dependentesPage.clicarConfirmarDependente();
    }

    @E("o usuário clicar no botão [Continuar] na tela de Dependentes")
    public void clicarContinuar() {
        dependentesPage.clicarContinuar();
    }
}
