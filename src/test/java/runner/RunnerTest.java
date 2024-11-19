package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "D:\\Users\\Fardim\\IdeaProjects\\ecommplanos_att\\src\\main\\resources\\features\\web\\planos\\base\\ECCMAUT-144.feature",
        glue = {"web.steps", "api.steps", "web.support.config"},
        plugin = {"pretty", "json:target/reports/CucumberReport.json"},
        snippets = SnippetType.CAMELCASE,
        tags = "@Regressivo")

public class RunnerTest {
}