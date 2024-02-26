package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import support.BaseSteps;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        glue = {"steps", "support"},
        plugin = "json:target/reports/CucumberReport.json",
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        stepNotifications = true,
        tags = "@bloqueioCEPdiferente")

public class  RunnerTest extends BaseSteps {
    //mvn test "-Denv=S6" "-Dcucumber.filter.tags=@bloqueioCEPdiferente"
}
