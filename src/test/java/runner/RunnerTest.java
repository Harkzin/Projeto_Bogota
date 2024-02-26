package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import support.BaseSteps;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        glue = {"steps", "support"},
        plugin = {"json:target/reports/CucumberReport.json"},
        snippets = SnippetType.CAMELCASE,
        stepNotifications = false,
        tags = "@bloqueioCEPdiferente")

public class  RunnerTest extends BaseSteps {
    //mvn clean test "-Denv=S6" "-Dcucumber.filter.tags=@bloqueioCEPdiferente"
}
