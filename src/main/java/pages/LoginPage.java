package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.User;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends BasePage{
    private final SelenideElement userNameTextBox = $x("//input[@id='username']");
    private final SelenideElement passwordTextBox = $x("//input[@id='password']");
    private final SelenideElement loginBtn = $x("//button[contains(@class, 'woocommerce-form-login__submit')]");

    public void enterLoginInfo(User user){
        userNameTextBox.setValue(user.getUsername());
        passwordTextBox.setValue(user.getPassword());
    }

    public void clickLoginButton(){
        loginBtn.click();
    }

    @Step("Login with account")
    public void loginWithAccount(User user){
        enterLoginInfo(user);
        clickLoginButton();
    }
}
