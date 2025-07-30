package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.UIAssertionError;
import data.enums.Departments;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import utils.WaitUtils;
import utils.WebUtils;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
@Log4j
public class BasePage{
    private final SelenideElement loginSignupMyAccountLink = $x("//div[contains(@class, 'login-link')]/a");
    private final SelenideElement shopPageLink = $x("//div[contains(@class, 'header-main-menu')]//li[contains(@class, 'menu-item')]/a[text()='Shop']");
    private final SelenideElement cartPageLink = $x("//div[contains(@class, 'cart-type1') and contains(@class, 'et-content-toTop')]/a");
    private final SelenideElement allDepartmentMenu = $x("//div[@class='secondary-title']/span[contains(text(), 'All departments')]");

    private static final SelenideElement popupMessage = $x("//div[contains(@class, 'pum-overlay') and contains(@style, 'display: block')]");
    private static final SelenideElement closeButton = $x("//div[@id='pum-5700']//button[contains(@class, 'pum-close') and contains(@class, 'popmake-close')]");
    private final SelenideElement loadingSpinner = $("div.et-loader.product-ajax > svg > circle");

    private static final SelenideElement cookieNotice = $("#cookie-notice");
    private static final SelenideElement acceptButton = $("#cn-accept-cookie");

    private final String departmentLink = "//ul[@id='menu-all-departments-1']/li/a[contains(text(), '%s')]";

    @Step("Go to Shop Page")
    public void goToShopPage() {
        shopPageLink.click();
        WaitUtils.waitForPageLoaded(20);
        closePopupMessageIfVisible();
    }

    @Step("Go to Login Page")
    public void goToLoginPage() {
        loginSignupMyAccountLink.click();
        WaitUtils.waitForPageLoaded(20);
    }

    @Step("Go to Cart Page")
    public void goToCartPage() {
        WebUtils.scrollToTop();
        WaitUtils.waitUntilClickable(cartPageLink, 5);
        cartPageLink.click();
        WaitUtils.waitForPageLoaded(20);
    }

    private SelenideElement getDepartmentLink(String departmentName) {
        return $x(String.format(departmentLink, departmentName));
    }

    @Step("Select \"{department.value}\" on Department menu")
    public void selectDepartment(Departments department) {
        allDepartmentMenu.hover();
        SelenideElement specificDepartment = getDepartmentLink(department.getValue());
        specificDepartment.click();
        WaitUtils.waitForPageLoaded(20);
    }

    public static void closePopupMessageIfVisible() {
        log.info("Checking for popup visibility...");

        try {
            popupMessage.should(Condition.appear, Duration.ofSeconds(10));
            log.info("Popup is visible, attempting to close...");
            closeButton.click();
            WaitUtils.waitForElementToDisappear(popupMessage, 10);
        } catch (ElementNotFound e) {
            log.warn("Popup or close button not found in DOM.");
        } catch (UIAssertionError e) {
            log.warn("Popup did not appear within timeout.");
        }
    }

    public static void acceptCookieIfVisible(){
        log.info("Checking for cookie notice...");

        if (cookieNotice.exists() && cookieNotice.isDisplayed()) {
            log.info("Cookie notice is visible. Accepting...");

            try {
                acceptButton.scrollIntoView(true);
                acceptButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
                acceptButton.click();
                cookieNotice.shouldBe(Condition.hidden, Duration.ofSeconds(5));
                log.info("Cookie notice dismissed.");
            } catch (Exception e) {
                log.warn("Failed to dismiss cookie notice: " + e.getMessage());
            }
        } else {
            log.info("No cookie notice present.");
        }
    }
}
