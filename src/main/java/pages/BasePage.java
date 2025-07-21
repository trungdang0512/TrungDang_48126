package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.enums.Departments;
import io.qameta.allure.Step;
import utils.WaitUtils;

import static com.codeborne.selenide.Selenide.$x;

public class BasePage{
    private final SelenideElement loginSignupMyAccountLink = $x("//div[contains(@class, 'login-link')]/a");
    private final SelenideElement shopPageLink = $x("//div[contains(@class, 'header-main-menu')]//li[contains(@class, 'menu-item')]/a[text()='Shop']");
    private final SelenideElement cartPageLink = $x("//div[contains(@class, 'cart-type1') and contains(@class, 'et-content-toTop')]/a");
    private final SelenideElement allDepartmentMenu = $x("//div[@class='secondary-title']/span[contains(text(), 'All departments')]");

    private final SelenideElement backToTopBtn = $x("//div[@id='back-top']");

    private final String departmentLink = "//ul[@id='menu-all-departments-1']/li/a[contains(text(), '%s')]";

    @Step("Go to Shop Page")
    public void goToShopPage() {
        shopPageLink.click();
        WaitUtils.waitForPageLoaded(20);
    }

    @Step("Go to Login Page")
    public void goToLoginPage() {
        loginSignupMyAccountLink.click();
        WaitUtils.waitForPageLoaded(20);
    }

    public void goToTopPage() {
        if (backToTopBtn.is(Condition.visible)) {
            backToTopBtn.click();
        }
    }

    @Step("Go to Cart Page")
    public void goToCartPage() {
        goToTopPage();
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
}
