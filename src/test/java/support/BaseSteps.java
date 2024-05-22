package support;

public class BaseSteps {
    protected DriverQA driverQA = new DriverQA();
    protected CartOrder cartOrder = new CartOrder();

    public BaseSteps() {
        String browser = System.getProperty("browser", "chrome");
        switch (browser) {
            case "chrome":
                driverQA.setupDriver("chrome");
                break;
            case "firefox":
                driverQA.setupDriver("firefox");
                break;
            default:
                throw new IllegalArgumentException("Invalid browser: " + browser);
        }
    }
}