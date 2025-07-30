package testTA;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import config.BrowserConfig;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import pages.*;
import utils.Constants;

import static com.codeborne.selenide.Selenide.*;

public class TA_TestBase {
    MainPage mainPage = new MainPage();
    LoginPage loginPage = new LoginPage();
    ShopAndProductCategoriesPage shopAndProductCategoriesPage = new ShopAndProductCategoriesPage();
    CartPage cartPage = new CartPage();
    CheckOutPage checkOutPage = new CheckOutPage();
    OrderStatusPage orderStatusPage = new OrderStatusPage();

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setUp(String browser) {
        BrowserConfig.setupBrowser(browser, 5000);
        open(Constants.TA_URL);
        WebDriverRunner.getWebDriver().manage().window().maximize();
        mainPage.closePopupMessageIfVisible();
        mainPage.acceptCookieIfVisible();
        Configuration.reportsFolder = "allure-results";
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        clearBrowserCookies();
        closeWebDriver();
    }
}
