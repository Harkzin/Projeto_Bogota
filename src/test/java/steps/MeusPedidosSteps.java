package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import pages.MeusPedidosPage;
import support.BaseSteps;

public class MeusPedidosSteps extends BaseSteps {

    MeusPedidosPage meusPedidosPage = new MeusPedidosPage(driverQA);

    @E("^validar que foi direcionado para a página de Meus Pedidos$")
    public void validaMeusPedidos() {
        meusPedidosPage.validaMeusPedidos();
    }

    @E("^validar que foi exibido o Número do pedido, Data do pedido e o Status do\\(s\\) pedido\\(s\\) já realizado\\(s\\) anteriormente$")
    public void validarSeEExibidoONumeroDataEOStatusDoSPedidoS() {
        meusPedidosPage.validarNumeroDataEStatus();
    }

    @Então("^validar que foi direcionado para a página de acompanhamento de pedido$")
    public void validarQueFoiDirecionadoParaAPaginaDeAcompanhamento() {
        meusPedidosPage.validarPaginaDePedido();
    }
}
