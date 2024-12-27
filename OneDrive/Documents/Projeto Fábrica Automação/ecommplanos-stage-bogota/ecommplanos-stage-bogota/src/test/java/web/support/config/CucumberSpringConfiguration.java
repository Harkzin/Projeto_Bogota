package web.support.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;

import web.models.CartOrder;

@CucumberContextConfiguration
@SuppressWarnings("UnusedDeclaration")
public final class CucumberSpringConfiguration {

    @TestConfiguration
    @ComponentScan({"web.support.utils", "web.support.config", "web.pages"})
    public static class Configuration {

        @Bean
        @Scope("cucumber-glue")
        public CartOrder createCart() {
            return new CartOrder();
        }
    }
}