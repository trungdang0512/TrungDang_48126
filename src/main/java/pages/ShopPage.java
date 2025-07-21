package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.WebDriverRunner.url;

public class ShopPage extends BasePage{
    @Step("Check Page")
    public String getCurrentUrl(){
        return url();
    }
}
