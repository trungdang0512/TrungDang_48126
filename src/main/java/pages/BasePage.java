package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class BasePage{
    private final SelenideElement shopPageLink = $x("//div[contains(@class, 'header-main-menu')]//li[contains(@class, 'menu-item')]/a[text()='Shop']");

    @Step("Go to Shop Page")
    public void goToShopPage() {
        shopPageLink.click();
    }
}
