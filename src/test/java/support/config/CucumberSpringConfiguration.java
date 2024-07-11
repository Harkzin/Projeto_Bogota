package support.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;

import support.CartOrder;
import support.utils.DriverQA;

@CucumberContextConfiguration
@SuppressWarnings("UnusedDeclaration")
public final class CucumberSpringConfiguration {

    @TestConfiguration
    @ComponentScan({"support.config", "pages"})
    public static class Configuration {
        @Bean
        @Scope("cucumber-glue")
        public DriverQA createDriverQA() {
            DriverQA driverQA = new DriverQA();
            driverQA.setupDriver(System.getProperty("browser", "chrome"));

            return driverQA;
        }

        @Bean
        @Scope("cucumber-glue")
        public CartOrder createCartOrder() {
            return new CartOrder();
        }
    }
}