package testutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverConfiguration {
    public static WebDriver getDriver(){
        System.setProperty(Constant.WEB_DRIVER_NAME, Constant.WEB_DRIVER_PATH);
        WebDriver driver=new ChromeDriver();
        return driver;
    }
}
