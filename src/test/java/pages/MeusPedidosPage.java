package pages;

import support.DriverQA;

public class MeusPedidosPage {
    private final DriverQA driver;

    public MeusPedidosPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    public void validaMeusPedidos() {
        //driver.waitElementVisibility("//div[contains(text(),'Meus Pedidos')]", "xpath");
    }

    public void validarNumeroDataEStatus() {
        //driver.waitElementVisibility("//p[contains(text(), 'Número do pedido')]", "xpath");
        //driver.waitElementVisibility("//p[contains(text(), 'Data do pedido')]", "xpath");
        //driver.waitElementVisibility("//p[contains(text(), 'Status')]", "xpath");
    }

    public void validarPaginaDePedido() {
        //driver.waitElementVisibility("//h1[contains(text(), 'Detalhes do pedido')]", "xpath");
    }
}