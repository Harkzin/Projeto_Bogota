package web.steps;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.PlpAparelhosPage;
import web.models.CartOrder;
import web.support.utils.DriverWeb;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class PlpAparelhosSteps {

    private final PlpAparelhosPage plpAparelhosPage;
    private final CartOrder cart;
    private final DriverWeb driverWeb;

    @Autowired
    public PlpAparelhosSteps (PlpAparelhosPage plpAparelhosPage, CartOrder cart, DriverWeb driverWeb) { //TODO Remover DriverQA quando ECCMAUT-806 finalizada
        this.plpAparelhosPage = plpAparelhosPage;
        this.cart = cart;
        this.driverWeb = driverWeb;
    }

    @Entao("é direcionado para a tela PLP de Aparelho")
    public void validarPLpAparelhos() {
        plpAparelhosPage.validarPlpAparelhos();
    }

    @Quando("o usuário clicar no botão [Eu quero!] do card do Aparelho {string}")
    public void clicarEuQuero(String id) throws ParseException {
        cart.setDevice(id, NumberFormat.getInstance(Locale.GERMAN).parse(driverWeb.findElement("//*[@id='btn-eu-quero-" + id + "']/../../preceding-sibling::input[contains(@name, 'productPrice')]", "xpath").getAttribute("value") + ",00").doubleValue()); //TODO Voltar para cartOrder.setDevice(id); quando a API estiver pronta - ECCMAUT-806
        plpAparelhosPage.validarCardAparelho(cart.getDevice());
        plpAparelhosPage.clicaBotaoEuQuero(id);
    }
}