package pages;

import support.DriverQA;

public class MeusPedidosPage {
    private DriverQA driver;

    public MeusPedidosPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    public void validaMeusPedidos() {
        driver.waitElementAll("//div[contains(text(),'Meus Pedidos')]", "xpath");
    }

    public void validarNumeroDataEStatus() {
        driver.waitElementAll("//p[contains(text(), 'Número do pedido')]", "xpath");
        driver.waitElementAll("//p[contains(text(), 'Data do pedido')]", "xpath");
        driver.waitElementAll("//p[contains(text(), 'Status')]", "xpath");
    }

    public void acessarPedidoMaisRecente() {
        driver.JavaScriptClick("(//a[@class='order-table-code color-bf-light'])[1]", "xpath");
    }

    public void validarPaginaDePedido() {
        driver.waitElementAll("//h1[contains(text(), 'Detalhes do pedido')]", "xpath");
    }
}
