package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        glue = {"steps", "support"},
        plugin = {"pretty", "json:target/reports/CucumberReport.json"},
        snippets = SnippetType.CAMELCASE,
        tags = "@TrocaControleRentabilizacaoDebito")

public class RunnerTest {
}