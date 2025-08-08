package config;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserConfig {
    public static void setupBrowser(String browser, int timeout){
        Configuration.timeout = timeout;

        if (browser.equalsIgnoreCase("edge")) {
            Configuration.browser = "edge";
        } else if (browser.equalsIgnoreCase("chrome")) {
            Configuration.browser = "chrome";
        }
    }
}
