package web.support.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;

import web.models.CartOrder;
import web.support.utils.DriverQA;

@CucumberContextConfiguration
@SuppressWarnings("UnusedDeclaration")
public final class CucumberSpringConfiguration {

    @TestConfiguration
    @ComponentScan({"web.support.config", "web.pages"})
    public static class Configuration {
        @Bean
        @Scope("cucumber-glue")
        public DriverQA createDriverQA() {
            DriverQA driverQA = new DriverQA();
            driverQA.setupDriver();

            return driverQA;
        }

        @Bean
        @Scope("cucumber-glue")
        public CartOrder createCartOrder() {
            return new CartOrder();
        }
    }
}