package steps;


import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Mas;
import org.springframework.beans.factory.annotation.Autowired;
import pages.ComumPage;
import support.CartOrder;
import support.utils.Constants;

public class ComumSteps {

    private final ComumPage comumPage;
    private final CartOrder cartOrder;

    @Autowired
    public ComumSteps(ComumPage comumPage, CartOrder cartOrder) {
        this.comumPage = comumPage;
        this.cartOrder = cartOrder;
    }

    @Mas("não deve haver alterações no valor e nem nas informações do Plano")
    public void validarResumoCompraPlano() {
        comumPage.validarResumoCompraPlano(cartOrder);
    }

    @Então("o valor do Plano e o método de pagamento serão atualizados no Resumo da compra")
    public void validarAlteraValorPagamento() {
        comumPage.validarResumoCompraPlano(cartOrder);
    }

    @Mas("não deve haver alterações no valor e nem nas informações do Aparelho")
    public void validarResumoCompraAparelho() {
        comumPage.validarResumoCompraAparelho(cartOrder, cartOrder.isEsim());
    }

    @E("o plano do carrinho será atualizado para o Plano Combo correspondente")
    public void atualizarParaPlanoCombo() {
        cartOrder.setPlan(Constants.planSingleToCombo.get(cartOrder.getPlan().getCode()));
        cartOrder.isDebitPaymentFlow = false; //TODO Valor deve ser de acordo com o tipo de pagamento da linha combo
        comumPage.validarResumoCompraPlano(cartOrder);
    }
}