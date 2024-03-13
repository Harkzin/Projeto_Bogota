package support;

public class BaseSteps {
    protected static DriverQA driver = new DriverQA();

    public BaseSteps() {
        String browser = System.getProperty("browser", "chrome");
        switch (browser) {
            case "chrome":
                driver.setupDriver("chrome");
                break;
            case "firefox":
                driver.setupDriver("firefox");
                break;
            default:
                throw new IllegalArgumentException("Invalid browser: " + browser);
        }
    }
}