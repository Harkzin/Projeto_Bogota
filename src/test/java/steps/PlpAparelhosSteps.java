package steps;

import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import pages.PlpAparelhosPage;
import support.CartOrder;
import support.utils.DriverQA;

public class PlpAparelhosSteps {

    private final PlpAparelhosPage plpAparelhosPage;
    private final CartOrder cartOrder;
    private final DriverQA driverQA;

    @Autowired
    public PlpAparelhosSteps (PlpAparelhosPage plpAparelhosPage, CartOrder cartOrder, DriverQA driverQA) { //TODO Remover DriverQA quando ECCMAUT-806 finalizada
        this.plpAparelhosPage = plpAparelhosPage;
        this.cartOrder = cartOrder;
        this.driverQA = driverQA;
    }

    @Então("é direcionado para a tela PLP de Aparelho")
    public void validarPLpAparelhos() {
        plpAparelhosPage.validarPlpAparelhos();
    }

    @Quando("o usuário clicar no botão [Eu quero!] do card do Aparelho {string}")
    public void clicarEuQuero(String id) {
        cartOrder.setDevice(id, Double.parseDouble(driverQA.findElement("//*[@id='btn-eu-quero-" + id + "']/../../preceding-sibling::input[contains(@name, 'productPrice')]", "xpath").getAttribute("value"))); //TODO Voltar para cartOrder.setDevice(id); quando a API estiver pronta - ECCMAUT-806
        plpAparelhosPage.validarCardAparelho(cartOrder.getDevice());
        plpAparelhosPage.clicaBotaoEuQuero(id);
    }
}