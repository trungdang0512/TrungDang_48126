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

        String runMode = System.getProperty("runMode", "local");
        if ("remote".equalsIgnoreCase(runMode)) {
            Configuration.remote = "http://localhost:4444/wd/hub"; // Selenium Grid hub
        } else {
            // Local thì để trống remote
            Configuration.remote = null;
        }
    }
}
