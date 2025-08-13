package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import models.Product;
import utils.Constants;
import utils.ElementUtils;
import utils.WaitUtils;
import utils.WebDriverUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

@Log4j
public class CartPage extends BasePage {

    private final ElementsCollection allProductsTitleOnCart = $$x("//td[@class='product-details']/div/a[@class='product-title']");
    private final ElementsCollection allProductsPriceOnCart = $$x("//td[@class='product-details']//span[contains(@class, 'woocommerce-Price-amount')]//bdi");
    private final ElementsCollection allProductsQuantityOnCart = $$x("//input[contains(@class, 'input-text qty text')]");
    private final ElementsCollection allProductsSubTotalOnCart = $$x("//td[@class='product-subtotal']//span[contains(@class, 'woocommerce-Price-amount')]//bdi");

    private final SelenideElement checkoutBtn = $x("//a[@href='https://demo.testarchitect.com/checkout/' and contains(@class, 'checkout-button')]");
    private final SelenideElement clearCartButton = $x("//a[contains(@class,'clear-cart')]");
    private final SelenideElement emptyCartMessage = $x("//div[contains(@class,'cart-empty') and contains(@class,'empty-cart-block')]//h1");

    private Product getProductFromCartByIndex(int index) {
        String titleText = allProductsTitleOnCart.get(index).getAttribute("textContent");
        String priceText = allProductsPriceOnCart.get(index).getAttribute("textContent");
        String quantityText = allProductsQuantityOnCart.get(index).getValue();
        String subTotalText = allProductsSubTotalOnCart.get(index).getAttribute("textContent");
        return new Product(titleText, priceText, quantityText, subTotalText);
    }

    public List<Product> getAllProductsOnCart() {
        List<Product> productList = new ArrayList<>();

        int count = Math.min(allProductsTitleOnCart.size(), allProductsPriceOnCart.size());//make sure 2 size are equal

        for (int i = 0; i < count; i++) {
            productList.add(getProductFromCartByIndex(i));
        }
        log.info("Products list on Cart: {}" + productList);
        return productList;
    }

    @Step("Check if selected product displayed on Cart")
    public boolean checkAddedProductDisplayedOnCart(Product selectedProduct, List<Product> productsList) {
        return productsList.stream()
                .anyMatch(product -> product.equalsByTitlePriceQuantityAndSubTotal(selectedProduct));
    }

    @Step("Check if all products in list are displayed on Cart")
    public boolean checkAddedProductsDisplayedOnCart(List<Product> selectedProducts, List<Product> actualProductsOnCart) {
        return selectedProducts.stream()
                .anyMatch(expectedProduct ->
                        actualProductsOnCart.stream()
                                .anyMatch(actualProduct -> actualProduct.equalsByTitlePriceQuantityAndSubTotal(expectedProduct))
                );
    }

    @Step("Click on Checkout Button")
    public void clickOnCheckoutBtn() {
        ElementUtils.refreshIfNotLoaded(checkoutBtn, Constants.MEDIUM_WAIT);
        WebDriverUtils.scrollToTop();
        WaitUtils.waitForElementToBeVisible(checkoutBtn, Constants.MEDIUM_WAIT);
        WaitUtils.waitUntilClickable(checkoutBtn, Constants.MEDIUM_WAIT);
        checkoutBtn.click();
        WaitUtils.waitForAjaxComplete();
    }

    @Step("Check if items show in table")
    public boolean checkProductsDisplayedOnCart() {
        ElementUtils.refreshIfNotLoaded(allProductsTitleOnCart, Constants.MEDIUM_WAIT);
        allProductsTitleOnCart.first().shouldBe(Condition.visible, Duration.ofSeconds(Constants.SHORT_WAIT));
        return allProductsTitleOnCart.size() > 0;
    }

    @Step("Click on Clear Cart button")
    public void clickOnClearCartBtn(){
        WaitUtils.waitForElementToBeVisible(clearCartButton, Constants.MEDIUM_WAIT);
        WaitUtils.waitUntilClickable(clearCartButton, Constants.MEDIUM_WAIT);
        clearCartButton.scrollIntoView(false).click();
        WebDriverUtils.handleAlert(true);
    }

    @Step("Check if Cart is empty")
    public boolean isCartEmpty(){
        boolean noProducts = allProductsTitleOnCart.isEmpty() || allProductsTitleOnCart.filter(Condition.visible).isEmpty();
        boolean messageDisplayed = emptyCartMessage.shouldBe(Condition.visible, Duration.ofSeconds(Constants.SHORT_WAIT)).exists();
        return noProducts && messageDisplayed;
    }
}
