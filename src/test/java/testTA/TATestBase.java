package testTA;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.BrowserConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.*;
import pages.*;
import utils.Constants;

import static com.codeborne.selenide.Selenide.*;
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class TATestBase {
    MainPage mainPage = new MainPage();
    LoginPage loginPage = new LoginPage();
    ShopAndProductCategoriesPage shopAndProductCategoriesPage = new ShopAndProductCategoriesPage();
    CartPage cartPage = new CartPage();
    CheckOutPage checkOutPage = new CheckOutPage();
    OrderStatusPage orderStatusPage = new OrderStatusPage();
    MyAccountPage myAccountPage = new MyAccountPage();
    ProductDetailPage productDetailPage = new ProductDetailPage();

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(String browser) {
        SelenideLogger.removeListener("AllureSelenide");

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(true).includeSelenideSteps(false));
        BrowserConfig.setupBrowser(browser, 5000);
        open(Constants.TA_URL);
        WebDriverRunner.getWebDriver().manage().window().maximize();
        mainPage.closePopupMessageIfVisible();
        mainPage.acceptCookieIfVisible();
        Configuration.reportsFolder = "allure-results";
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        clearBrowserCookies();
        closeWebDriver();
    }


}
