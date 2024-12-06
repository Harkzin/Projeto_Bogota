package web.steps;

import org.springframework.beans.factory.annotation.Autowired;

<<<<<<< HEAD
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Mas;
import web.models.CartOrder;
import web.pages.ComumPage;
import static web.support.utils.Constants.planSingleToCombo;

=======
>>>>>>> Projeto_Bogota_o/stage-bogota
public class ComumSteps {

    private final ComumPage comumPage;
    private final CartOrder cart;

    @Autowired
    public ComumSteps(ComumPage comumPage, CartOrder cart) {
        this.comumPage = comumPage;
        this.cart = cart;
    }

    @Mas("não deve haver alterações no valor e nem nas informações do Plano")
    public void validarResumoCompraPlano() {
        comumPage.validarResumoCompraPlano(cart);
    }
    @Mas("não deve haver alterações no valor e nem nas informações do Plano Pos")
    public void validarResumoCompraPlanoPos() {
        comumPage.validarResumoCompraPlanoPos(cart);
    }
    @Mas("não deve haver alterações no valor e nem nas informações do Plano Controle")
    public void validarResumoCompraPlanoControle() {
        comumPage.validarResumoCompraPlanoControle(cart);
    }

    @Entao("o valor do Plano e o método de pagamento serão atualizados no Resumo da compra para Débito/Boleto")
    public void validarAlteraValorPagamento() {
        comumPage.validarResumoCompraPlano(cart);
    }

    @Entao("o valor do Plano será atualizado no Resumo da compra para fatura impressa")
    public void validarValorFaturaImpressa() {
        comumPage.validarResumoCompraPlano(cart);
    }

    @Entao("o valor do Plano será atualizado no Resumo da compra para fatura digital")
    public void validarValorFaturaDigital() {
        comumPage.validarResumoCompraPlano(cart);
    }

    @Mas("não deve haver alterações no valor e nem nas informações do Aparelho")
    public void validarResumoCompraAparelho() {
        comumPage.validarResumoCompraAparelho(cart);
    }

    @E("o plano do carrinho será atualizado para o Plano Combo correspondente")
    public void atualizarParaPlanoCombo() {
        cart.updatePlanCartPromotion();
        comumPage.validarResumoCompraPlano(cart);
    }

    @Entao("é direcionado para a tela de Carrinho com a oferta")
    public void validarResumoRentab() {
        comumPage.validarResumoCompraPlano(cart);
    }
}