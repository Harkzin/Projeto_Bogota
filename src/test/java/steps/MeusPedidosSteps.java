package steps;

import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import pages.MeusPedidosPage;
import support.BaseSteps;

public class MeusPedidosSteps extends BaseSteps {

    MeusPedidosPage meusPedidosPage = new MeusPedidosPage(driver);

    @E("^validar que foi direcionado para a página de Meus Pedidos$")
    public void validaMeusPedidos() {
        meusPedidosPage.validaMeusPedidos();
    }

    @E("^validar que foi exibido o Número do pedido, Data do pedido e o Status do\\(s\\) pedido\\(s\\) já realizado\\(s\\) anteriormente$")
    public void validarSeEExibidoONumeroDataEOStatusDoSPedidoS() {
        meusPedidosPage.validarNumeroDataEStatus();
    }

    @E("^acessar o pedido mais recente, clicando no Número do pedido dele$")
    public void acessarOPedidoMaisRecente() {
        meusPedidosPage.acessarPedidoMaisRecente();
    }

    @Entao("^validar que foi direcionado para a página de acompanhamento de pedido$")
    public void validarQueFoiDirecionadoParaAPaginaDeAcompanhamento() {
        meusPedidosPage.validarPaginaDePedido();
    }
}
