package support;

public class BaseSteps {
    protected static DriverQA driver = new DriverQA();
    public BaseSteps() {
        String browser = System.getProperty("browser", "chrome");
        switch (browser) {
            case "chrome":
                browser = driver.start("chrome");
                break;
            case "firefox":
                browser = driver.start("firefox");
                break;
            default:
                throw new IllegalArgumentException("Navegador inválido: " + browser);
        }
    }
}