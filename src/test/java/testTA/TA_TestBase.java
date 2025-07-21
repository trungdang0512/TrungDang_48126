package testTA;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.Constants;
import utils.CookieHandler;
import utils.PopupHandler;

import static com.codeborne.selenide.Selenide.*;

public class TA_TestBase {
    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setUp(String browser) {
        if (browser.equalsIgnoreCase("edge")) {
            Configuration.browser = "edge";
        } else if (browser.equalsIgnoreCase("chrome")) {
            Configuration.browser = "chrome";
        }

        Configuration.timeout = 5000;
        open(Constants.TA_URL);
        PopupHandler.closePopupIfPresent();

        WebDriverRunner.getWebDriver().manage().window().maximize();
        CookieHandler.acceptCookieIfVisible();
        Configuration.reportsFolder = "allure-results";
    }



    @AfterClass(alwaysRun = true)
    public void tearDown() {
        closeWebDriver();
    }
}
