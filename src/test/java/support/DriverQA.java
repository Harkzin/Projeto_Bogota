package support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.nodes.Document;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ComumPage.Email;

import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofSeconds;
import static support.RestAPI.getEmailMessage;

public class DriverQA {
    private static WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setupDriver(String browser) {
        String headless = System.getProperty("headless", "true");

        String title = "";
        try {
            title = driver.getTitle();
        } catch (Exception e) {
            title = "ERROR";
        }
        if (title.equals("ERROR")) {
            switch (browser) {
                case "firefox":
                    WebDriverManager.firefoxdriver().clearDriverCache().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless.equals("true")) {
                        firefoxOptions.addArguments("--headless");
                    }
                    firefoxOptions.addArguments("--private");
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case "chrome":
                    WebDriverManager.chromedriver().clearDriverCache().clearResolutionCache().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless.equals("true")) {
                        chromeOptions.addArguments("--headless");
                    }
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--no-default-browser-check");
                    chromeOptions.addArguments("--disable-default-apps");
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--deny-permission-prompts");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser: " + browser);
            }

            //driver.manage().window().setSize(new Dimension(1920, 1080));
            driver.manage().window().maximize();
        }
    }

    public WebElement findElement(String selectorValue, String selectorType) {
        WebElement element;

        try {
            switch (selectorType) {
                case "id":
                    element = driver.findElement(By.id(selectorValue));
                    break;
                case "linkt":
                    element = driver.findElement(By.linkText(selectorValue));
                    break;
                case "partialLink":
                    element = driver.findElement(By.partialLinkText(selectorValue));
                    break;
                case "name":
                    element = driver.findElement(By.name(selectorValue));
                    break;
                case "tag":
                    element = driver.findElement(By.tagName(selectorValue));
                    break;
                case "xpath":
                    element = driver.findElement(By.xpath(selectorValue));
                    break;
                case "class":
                    element = driver.findElement(By.className(selectorValue));
                    break;
                case "css":
                    element = driver.findElement(By.cssSelector(selectorValue));
                    break;
                default:
                    throw new InvalidArgumentException("Invalid selector type: " + selectorType);
            }
            return element;
        } catch (Exception e) {
            return null;
        }
    }

    public List<WebElement> findElements(String selectorValue, String selectorType) {
        List<WebElement> elements;

        switch (selectorType) {
            case "id":
                elements = driver.findElements(By.id(selectorValue));
                break;
            case "link":
                elements = driver.findElements(By.linkText(selectorValue));
                break;
            case "partialLink":
                elements = driver.findElements(By.partialLinkText(selectorValue));
                break;
            case "name":
                elements = driver.findElements(By.name(selectorValue));
                break;
            case "tag":
                elements = driver.findElements(By.tagName(selectorValue));
                break;
            case "xpath":
                elements = driver.findElements(By.xpath(selectorValue));
                break;
            case "class":
                elements = driver.findElements(By.className(selectorValue));
                break;
            case "css":
                elements = driver.findElements(By.cssSelector(selectorValue));
                break;
            default:
                throw new InvalidArgumentException("Invalid selector type: " + selectorType);
        }
        return elements;
    }

    public void actionSendKeys(WebElement element, String text) {
        Actions action = new Actions(driver);
        action.click(element);
        text.chars().forEach(c -> action.pause(Duration.ofMillis(50)).sendKeys(String.valueOf((char) c)).perform());
    }

    public void actionSendKeys(String selectorValue, String selectorType, String text) {
        actionSendKeys(findElement(selectorValue, selectorType), text);
    }

    public Document getEmail(String emailAddress, Email emailSubject) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(60))
                .withMessage("Aguardando recebimento do e-mail")
                .pollingEvery(Duration.ofSeconds(5));
        return wait.until(a -> getEmailMessage(emailAddress, emailSubject));
    }

    public void waitElementToBeClickable(WebElement element, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitElementVisibility(WebElement element, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitElementInvisibility(WebElement element, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void JavaScriptClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
    }

    public void JavaScriptClick(String selectorValue, String selectorType) {
        JavaScriptClick(findElement(selectorValue, selectorType));
    }

    public void waitPageLoad(String urlFraction, Integer timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.urlContains(urlFraction));

        wait.until(driver -> "complete".equals(((JavascriptExecutor) driver).executeScript("return document.readyState")));
    }
}