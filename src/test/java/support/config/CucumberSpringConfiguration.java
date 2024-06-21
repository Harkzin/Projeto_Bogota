package support.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import support.CartOrder;
import support.utils.DriverQA;

@CucumberContextConfiguration
@SuppressWarnings("UnusedDeclaration")
public final class CucumberSpringConfiguration {

    @TestConfiguration
    @ComponentScan({"support.config", "pages"})
    @SuppressWarnings("UnusedDeclaration")
    public static class Configuration {
        @Bean
        @SuppressWarnings("UnusedDeclaration")
        public DriverQA createDriverQA() {
            DriverQA driverQA = new DriverQA();
            driverQA.setupDriver(System.getProperty("browser", "chrome"));

            return driverQA;
        }

        @Bean
        @SuppressWarnings("UnusedDeclaration")
        public CartOrder createCartOrder() {
            return new CartOrder().initializeDefaultCartOrder();
        }
    }
}
