package web.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.cucumber.java.pt.E;

import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import web.models.CartOrder;

import java.net.http.HttpResponse;

import static java.time.Duration.*;
import static web.support.api.RestAPI.*;

public class ValidateOrderSteps {

    private final CartOrder cart;

    @Autowired
    public ValidateOrderSteps(CartOrder cart) {
        this.cart = cart;
    }

    private CartOrder order;
    private final Logger logger = LoggerFactory.getLogger(ValidateOrderSteps.class);

    @E("os dados do pedido estão corretos")
    public void validarDadosPedido() {
        final int VALIDATE_ORDER_TIMEOUT = 600;
        final int GET_ORDER_UPDATE_INTERVAL = 30;

        FluentWait<CartOrder> wait = new FluentWait<>(order)
                .withTimeout(ofSeconds(VALIDATE_ORDER_TIMEOUT))
                .pollingEvery(ofSeconds(GET_ORDER_UPDATE_INTERVAL));

        logger.info("------------------------------------------------------------------------");
        logger.info("Validate order: START");
        logger.info("------------------------------------------------------------------------");

        wait.until(o -> {
            order = refreshOrder();
            logger.info("Current order status: {} | Next update in {}s", order.getStatus(), GET_ORDER_UPDATE_INTERVAL);

            return order.getStatus().equals("AWAITING_INVOICE");
        });

        logger.info("------------------------------------------------------------------------");
        logger.info("Validate order: END");
        logger.info("------------------------------------------------------------------------");
        logger.info("Final order status: {}", order.getStatus());
    }

    private CartOrder refreshOrder() {
        final int ORDER_STATUS_REQUEST_ATTEMPT_INTERVAL = 30;
        final int ORDER_STATUS_REQUEST_MAX_RETRY = 3;

        final JsonMapper jsonMapper = JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
                .build();

        HttpResponse<String> orderStatusResponse;

        int attemptNumber = 1;
        while (true) {
            logger.info("REQUEST_ORDER_STATUS | Attempt: {}/{}", attemptNumber, ORDER_STATUS_REQUEST_MAX_RETRY);
            orderStatusResponse = orderStatusRequest(cart.getCode());

            if (orderStatusResponse.statusCode() != 200) {
                logger.error("RESPONSE_ORDER_STATUS - Error | Uri: {} | HttpCode: {} | Body:\n{}", orderStatusResponse.uri(), orderStatusResponse.statusCode(), orderStatusResponse.body());

                if (attemptNumber < ORDER_STATUS_REQUEST_MAX_RETRY) {
                    logger.info("Retrying in {} seconds", ORDER_STATUS_REQUEST_ATTEMPT_INTERVAL);
                } else {
                    logger.warn("RESPONSE_ORDER_STATUS - Max retry attempt reached");
                    throw new RuntimeException("RESPONSE_ORDER_STATUS - Unexpected response");
                }

                attemptNumber++;

                Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;
                try {
                    sleeper.sleep(ofSeconds(ORDER_STATUS_REQUEST_ATTEMPT_INTERVAL));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                logger.info("RESPONSE_ORDER_STATUS | OK");
                break;
            }
        }

        try {
            return jsonMapper.readValue(orderStatusResponse.body(), CartOrder.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}