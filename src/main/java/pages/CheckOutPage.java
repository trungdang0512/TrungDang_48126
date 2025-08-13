package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import models.BillingInfo;
import models.Product;
import org.openqa.selenium.By;
import utils.Constants;
import utils.WaitUtils;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

@Log4j
public class CheckOutPage extends BasePage {
    private final ElementsCollection allProductsTitleOnOrder = $$x("//td[@class='product-name']");
    private final ElementsCollection allProductsQuantityOnOrder = $$x("//strong[@class='product-quantity']");
    private final ElementsCollection allProductsSubTotalOnOrder = $$x("//td[@class='product-total']//span[contains(@class, 'woocommerce-Price-amount')]//bdi");

    private final SelenideElement firstNameTextBox = $(By.id("billing_first_name"));
    private final SelenideElement lastNameTextBox = $(By.id("billing_last_name"));
    private final SelenideElement companyNameTextBox = $(By.id("billing_company"));
    private final SelenideElement countrySelector = $(By.id("billing_country"));
    private final SelenideElement address1TextBox = $(By.id("billing_address_1"));
    private final SelenideElement address2TextBox = $(By.id("billing_address_2"));
    private final SelenideElement cityTextBox = $(By.id("billing_city"));
    private final SelenideElement stateTextBox = $(By.id("select2-billing_state-container"));
    private final SelenideElement postCodeTextBox = $(By.id("billing_postcode"));
    private final SelenideElement phoneTextBox = $(By.id("billing_phone"));
    private final SelenideElement emailTextBox = $(By.id("billing_email"));
    private final SelenideElement orderCommentsTextBox = $(By.id("order_comments"));
    private final SelenideElement placeOrderBtn = $x("//button[@id='place_order']");
    private final SelenideElement errorCheckoutMessage = $x("//div[contains(@class, 'woocommerce-NoticeGroup') and contains(@class, 'woocommerce-NoticeGroup-checkout')]");
    private final ElementsCollection allFields = $$x("//div[@class='woocommerce-billing-fields__field-wrapper']/p");

    private final SelenideElement bankTransferRadio = $x("//input[@id='payment_method_bacs' and @type='radio']");
    private final SelenideElement checkPaymentRadio = $x("//input[@id='payment_method_cheque' and @type='radio']");
    private final SelenideElement codRadio = $x("//input[@id='payment_method_cod' and @type='radio']");

    @Step("Check Checkout Page displays")
    public boolean isCheckoutPage() {
        return WebDriverUtils.isUrlContains("/checkout");
    }

    private Product getProductFromOrderByIndex(int index) {
        String titleText = allProductsTitleOnOrder.get(index).getText().split("×")[0].trim();
        String quantityText = allProductsQuantityOnOrder.get(index).getText().replaceAll("[^0-9]", "");
        String subTotalText = allProductsSubTotalOnOrder.get(index).getAttribute("textContent");
        return new Product(titleText, quantityText, subTotalText);
    }

    public List<Product> getAllProductsOnOrder() {
        List<Product> productList = new ArrayList<>();
        int count = Math.min(allProductsTitleOnOrder.size(), allProductsQuantityOnOrder.size());
        for (int i = 0; i < count; i++) {
            productList.add(getProductFromOrderByIndex(i));
        }
        log.info("Products list on Order: {}" + productList);
        return productList;
    }

    @Step("Check if selected product displayed on Order")
    public boolean checkSelectedProductDisplayedOnOrder(Product selectedProduct, List<Product> productsOnOrder) {
        return productsOnOrder.stream()
                .anyMatch(product -> product.equalsByTitleQuantityAndSubTotal(selectedProduct));
    }

    private void enterFirstNameTextBox(String firstName) {
        if (firstName != null) {
            firstNameTextBox.setValue(firstName);
        }
    }

    private void enterLastNameTextBox(String lastName) {
        if (lastName != null) {
            lastNameTextBox.setValue(lastName);
        }
    }

    private void enterCompanyNameTextBox(String companyName) {
        if (companyName != null) {
            companyNameTextBox.setValue(companyName);
        }
    }

    private void selectCountrySelector(String country) {
        countrySelector.selectOption(country);
    }

    private void enterAddress1TextBox(String address1) {
        if (address1 != null) {
            address1TextBox.setValue(address1);
        }
    }

    private void enterAddress2TextBox(String address2) {
        if (address2 != null) {
            address2TextBox.setValue(address2);
        }
    }

    private void enterCityTextBox(String city) {
        if (city != null) {
            cityTextBox.setValue(city);
        }
    }

    private void enterStateTextBox(String state) {

        if (stateTextBox.exists() && stateTextBox.is(visible)) {
            if (state != null) {
                String currentText = stateTextBox.getText().trim();
                if (currentText.equalsIgnoreCase("Select an option…")) {
                    stateTextBox.click();
                    $$(".select2-results__option")
                            .findBy(text(state))
                            .shouldBe(visible)
                            .click();
                }
            }
        }
    }

    private void enterPostCodeTextBox(String postCode) {
        if (postCode != null) {
            postCodeTextBox.setValue(postCode);
        }
    }

    private void enterPhoneTextBox(String phone) {
        if (phone != null) {
            phoneTextBox.setValue(phone);
        }
    }

    private void enterEmailTextBox(String email) {
        if (email != null) {
            emailTextBox.setValue(email);
        }
    }

    private void enterOrderCommentsTextBox(String comment) {
        if (comment != null) {
            orderCommentsTextBox.setValue(comment);
        }
    }

    @Step("Enter billing details")
    public void enterBillingDetails(BillingInfo billingInfo) {
        enterFirstNameTextBox(billingInfo.getFirstName());
        enterLastNameTextBox(billingInfo.getLastName());
        enterCompanyNameTextBox(billingInfo.getCompanyName());
        selectCountrySelector(billingInfo.getCountry());
        enterAddress1TextBox(billingInfo.getAddress1());
        enterAddress2TextBox(billingInfo.getAddress2());
        enterCityTextBox(billingInfo.getCity());
        enterStateTextBox(billingInfo.getState());
        enterPostCodeTextBox(billingInfo.getPostCode());
        enterPhoneTextBox(billingInfo.getPhone());
        enterEmailTextBox(billingInfo.getEmail());
        enterOrderCommentsTextBox(billingInfo.getOrderComments());
    }


    @Step("Click on PLACE ORDER")
    public void clickOnPlaceOrderBtn() {
        placeOrderBtn.scrollIntoView(false).click();
        WaitUtils.waitForAjaxComplete();
    }

    @Step("Select Check payment method")
    public void selectCheckPaymentMethod() {
        checkPaymentRadio.scrollIntoView(false).setSelected(true);
    }

    @Step("Select Bank transfer payment method")
    public void selectBacsPaymentMethod() {
        bankTransferRadio.scrollIntoView(false).setSelected(true);
    }

    @Step("Select COD payment method")
    public void selectCODPaymentMethod() {
        codRadio.scrollIntoView(false).setSelected(true);
    }

    public boolean isCheckoutNoticeGroupDisplayed() {
        return errorCheckoutMessage.isDisplayed();
    }

    public boolean hasInvalidRequiredField() {
        return allFields.stream()
                .anyMatch(field -> {
                    String classes = field.getAttribute("class");
                    return classes != null
                            && classes.contains("woocommerce-invalid")
                            && classes.contains("woocommerce-invalid-required-field");
                });
    }
}
