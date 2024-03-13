package support;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

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
                    WebDriverManager.chromedriver().clearDriverCache().setup();
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
            driver.manage().timeouts().implicitlyWait(ofSeconds(15));
            driver.manage().window().maximize();
        }
    }

    public WebElement findElement(String selectorValue, String selectorType) {
        WebElement element;

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

    public void click(String selectorValue, String selectorType) {
        WebElement element = findElement(selectorValue, selectorType);
        element.click();
    }

    public void actionClick(String selectorValue, String selectorType) {
        WebElement element = findElement(selectorValue, selectorType);
        Actions act = new Actions(driver);
        act.moveToElement(element);
        act.click(element).perform();
    }

    public void submit(String selectorValue, String selectorType) {
        WebElement element = findElement(selectorValue, selectorType);
        element.submit();
    }

    public void clear(String selectorValue, String selectorType) {
        WebElement element = findElement(selectorValue, selectorType);
        element.clear();
    }

    public void openURL(String url) {
        driver.get(url);
    }

    public void quit() {
        driver.quit();
    }

    public void close() {
        driver.close();
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public boolean isEnabled(String selectorValue, String selectorType) {
        WebElement element = findElement(selectorValue, selectorType);
        return element.isEnabled();
    }

    public boolean isDisplayed(String selectorValue, String selectorType) {
        WebElement element = findElement(selectorValue, selectorType);
        return element.isDisplayed();
    }

    public boolean isSelected(String selectorValue, String selectorType) {
        WebElement element = findElement(selectorValue, selectorType);
        return element.isSelected();
    }

    public void sendKeys(String selectorValue, String selectorType, String text) {
        WebElement element = findElement(selectorValue, selectorType);
        element.sendKeys(text);
    }

    public void actionSendKey(String selectorValue, String selectorType, String text) throws InterruptedException {
        WebElement element = findElement(selectorValue, selectorType);
        element.click();
        Actions action = new Actions(driver);
        char[] digits = text.toCharArray();
        for (char digit : digits) {
            String sDigit = Character.toString(digit);
            action.sendKeys(sDigit).perform();
            Thread.sleep(50);
        }
    }

    public String getText(String selectorValue, String selectorType) {
        WebElement element = findElement(selectorValue, selectorType);
        return element.getText();
    }

    public String getValue(String selectorValue, String selectorType) {
        WebElement element = findElement(selectorValue, selectorType);
        return element.getAttribute("value");
    }

    public String getValueParam(String selectorValue, String selectorType, String attribute) {
        WebElement element = findElement(selectorValue, selectorType);
        return element.getAttribute(attribute);
    }

    public void waitSeconds(int time) {
        driver.manage().timeouts().implicitlyWait(ofSeconds(time));
    }

    public void waitMilliSeconds(int time) {
        driver.manage().timeouts().implicitlyWait(ofMillis(time));
    }

    public void waitElement(String selectorValue, String selectorType) {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(15));

        switch (selectorType) {
            case "id":
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(selectorValue)));
                break;
            case "link":
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(selectorValue)));
                break;
            case "partialLink":
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(selectorValue)));
                break;
            case "name":
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(selectorValue)));
                break;
            case "xpath":
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectorValue)));
                break;
            case "class":
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(selectorValue)));
                break;
            case "css":
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selectorValue)));
                break;
            default:
                throw new IllegalArgumentException("Invalid selector type: " + selectorType);
        }
    }

    public void waitElementNotVisible(String parId) {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(60));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(parId)));
    }

    public void waitElementNotVisibleCSS(String parCss) {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(60));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(parCss)));
        driver.getWindowHandles();
    }

    public void waitElementNotVisibleXP(String parXp) {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(60));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(parXp)));
    }

    public void switchToWindow(String value) {
        driver.switchTo().window(String.valueOf(value));
    }

    public void switchToFrameId(String parId) {
        driver.switchTo().frame(driver.findElement(By.id(parId)));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void moveToElementAction(String selectorValue, String selectorType) {
        WebElement element = findElement(selectorValue, selectorType);
        try {
            Actions action = new Actions(driver);
            action.moveToElement(element);
            waitSeconds(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void browserScroll(String direction, int coordinate) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            switch (direction) {
                case "up":
                    js.executeScript("window.scrollBy(0,-" + coordinate + ")");
                    break;

                case "down":
                    js.executeScript("window.scrollBy(0," + coordinate + ")");
                    break;

                case "left":
                    js.executeScript("window.scrollBy(-" + coordinate + ",0)");
                    break;

                case "right":
                    js.executeScript("window.scrollBy(" + coordinate + ",0)");
                    break;
            }

            waitSeconds(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitElementToBeClickable(String selectorValue, String selectorType, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeOut));
        switch (selectorType) {
            case "id":
                if (wait.until(ExpectedConditions.elementToBeClickable(By.id(selectorValue))) != null) return;
                break;
            case "name":
                if (wait.until(ExpectedConditions.elementToBeClickable(By.name(selectorValue))) != null) return;
                break;
            case "css":
                if (wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selectorValue))) != null) return;
                break;
            case "xpath":
                if (wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selectorValue))) != null) return;
                break;
            case "link":
                if (wait.until(ExpectedConditions.elementToBeClickable(By.linkText(selectorValue))) != null) return;
                break;
            case "class":
                if (wait.until(ExpectedConditions.elementToBeClickable(By.className(selectorValue))) != null) return;
                break;
            default:
                throw new IllegalArgumentException("Invalid selector type: " + selectorType);
        }
    }

    public List<WebElement> findListElements(String selectorValue, String selectorType) {
        return findElements(selectorValue, selectorType);
    }

    public void sendKeysCampoMascara(String value, String selectorValue, String selectorType) {
        try {
            WebElement element = findListElements(selectorValue, selectorType).get(0);
            char[] digits = value.toCharArray();
            for (char digit : digits) {
                String sDigit = Character.toString(digit);
                element.sendKeys(sDigit);
                Thread.sleep(20);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendTab(int repeticao, String texto) {
        Actions actions = new Actions(driver);
        for (int i = 0; i < repeticao; i++) {
            waitSeconds(1);
            actions.sendKeys(Keys.TAB, texto).build().perform();
        }
    }

    public void sendKeyBoard(Keys keys) {
        Actions actions = new Actions(driver);
        actions.sendKeys(keys).build().perform();
    }

    public void JavaScriptClick(String selectorValue, String selectorType) {
        try {
            WebElement element = findElement(selectorValue, selectorType);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveToElementJs(String selectorValue, String selectorType) {
        WebElement element = findElement(selectorValue, selectorType);

        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            waitSeconds(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCookies() {
        Cookie cookie = driver.manage().getCookieNamed("claro-cart");
        int position = cookie.toString().indexOf(";");
        return cookie.toString().substring(0, position);
    }

    public boolean isEnabledDisplayed(String parValue, String parType) {
        WebElement element = findElement(parValue, parType);
        return element.isEnabled() && element.isDisplayed();
    }

    public void createNewTab() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open()");
    }

    public void changeTab(String numeroAba) {
        try {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(Integer.parseInt(numeroAba)));
        } catch (Exception ignored) {
        }
    }
}