package steps;

import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Mas;
import pages.ComumPage;
import support.CartOrder;

public class ComumSteps {

    private final ComumPage comumPage;
    private final CartOrder cartOrder;

    public ComumSteps(ComumPage comumPage, CartOrder cartOrder) { //Spring Autowired
        this.comumPage = comumPage;
        this.cartOrder = cartOrder;
    }

    @Mas("não deve haver alterações no valor e nem nas informações do Plano")
    public void validarResumoCompraPlano() {
        comumPage.validarResumoCompraPlano();
    }

    @Então("o valor do Plano e o método de pagamento serão atualizados no Resumo da compra")
    public void validarAlteraValorPagamento() {
        comumPage.validarResumoCompraPlano();
    }

    @Mas("não deve haver alterações no valor e nem nas informações do Aparelho")
    public void validarResumoCompraAparelho() {
        comumPage.validarResumoCompraAparelho();
    }
}