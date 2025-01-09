package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;


@Component
@ScenarioScope
public class PlpAcessoriosPage {
    private final DriverWeb driverWeb;

    @Autowired
    public PlpAcessoriosPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    public void validarPlpAcessorios() {
        driverWeb.waitPageLoad("/accessories", 5);
    }

    public void validarTodasOfertas() {
        driverWeb.javaScriptClick("//p[contains(text(), 'Todas as Ofertas')]/../div", "xpath");
    }

    public void clicarBotaoComprar(String acessorio) {
    driverWeb.javaScriptClick("btn-comprar-" + acessorio, "id");
    }
}
