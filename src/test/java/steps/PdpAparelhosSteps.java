package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.PdpAparelhosPage;
import support.BaseSteps;

public class PdpAparelhosSteps extends BaseSteps {

    PdpAparelhosPage pdpAparelhosPage = new PdpAparelhosPage(driverQA);

    @Entao("é direcionado para a tela para tela PDP de Aparelho")
    public void validoQueFoiDirecionadoParaAPDPDeAparelhos() {
        pdpAparelhosPage.validarRedirecionamentoParaPdpAparelhos();
    }

    @Entao("é direcionado para a tela para tela PDP de Aparelho selecionado")
    public void validoQueFoiDirecionadoParaAPDPDeAparelhosSelecionado() {
        pdpAparelhosPage.validarRedirecionamentoProduto();
    }

    @Quando("o úsuario seleciono a cor [Azul Safira] do aparelho")
    public void selecionoACordoAparelho() {
        pdpAparelhosPage.selecionarCorAparelho();
    }

    @Entao("será informado que não há estoque")
    public void deveMostrarProdutoEsgotado() {
        pdpAparelhosPage.validarProdutoEstoque();
    }


    @Quando("seleciono a opcao tres {string} da secao Sobre seu pedido na PDP de Aparelhos")
    public void selecionoAOpcaoTresDaSecaoSobreSeuPedidoNaPDPDeAparelhos(String string) {
        pdpAparelhosPage.marcarQueroUmaLinhaNovaDaClaro();
    }

    @E("seleciono a opcao um {string} da seção Plano Selecionado na PDP de Aparelhos")
    public void selecionoAOpcaoUmDaSeçãoPlanoSelecionadoNaPDPDeAparelhos(String string) {
        pdpAparelhosPage.selecionarClaroControle();
    }

    @E("seleciono o plano {string} do carrossel com o o valor {string} na PDP de Aparelhos")
    public void selecionoOPlanoDoCarrosselComOOValorNaPDPDeAparelhos(String string, String string2) {
        pdpAparelhosPage.selecionarPlano();
    }

    @E("clico no botao Comprar na PDP de Aparelhos")
    public void clicoNoBotaoComprarNaPDPDeAparelhos() {
        pdpAparelhosPage.clicarBotaoComprar();
    }

    @Quando("o usuário clicar no botão [Eu quero!] do card do Aparelho {string}")
    public void clicarEuQuero(String id) {
        pdpAparelhosPage.clicaBotaoEuQuero(id);
    }
}
