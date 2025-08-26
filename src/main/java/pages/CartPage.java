package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import models.Product;
import utils.*;

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
    private final SelenideElement updateCartButton = $x("//button[contains(@class,'btn') and contains(@class,'gray') and contains(@class,'medium') and contains(@class,'bordered') and @name='update_cart']");

    private final String quantityInputInCartByProductTitleXpath = "//tr[contains(@class,'cart_item')]//a[@class='product-title' and normalize-space(text())='%s']/ancestor::tr//td[@class='product-quantity']//input[contains(@class,'qty')]";
    private final String plusButtonByProductTitleXpath = "//tr[contains(@class,'cart_item')]//a[@class='product-title' and normalize-space(text())='%s']/ancestor::tr//span[contains(@class,'plus')]";
    private final String minusButtonByProductTitleXpath = "//tr[contains(@class,'cart_item')]//a[@class='product-title' and normalize-space(text())='%s']/ancestor::tr//span[contains(@class,'minus')]";
    private final String subtotalByProductTitleXpath = "//tr[contains(@class,'cart_item')]//a[@class='product-title' and normalize-space(text())='%s']/ancestor::tr//td[contains(@class,'product-subtotal')]";

    private Product getProductFromCartByIndex(int index) {
        String titleText = allProductsTitleOnCart.get(index).getAttribute("textContent");
        String priceText = allProductsPriceOnCart.get(index).getAttribute("textContent");
        int quantity = Integer.parseInt(allProductsQuantityOnCart.get(index).getValue());
        String subTotalText = allProductsSubTotalOnCart.get(index).getAttribute("textContent");
        return new Product(titleText, priceText, quantity, subTotalText);
    }

    public List<Product> getAllProductsOnCart() {
        List<Product> productList = new ArrayList<>();

        int count = Math.min(allProductsTitleOnCart.size(), allProductsPriceOnCart.size());//make sure 2 size are equal

        for (int i = 0; i < count; i++) {
            productList.add(getProductFromCartByIndex(i));
            log.info("Product on Cart: " + getProductFromCartByIndex(i).getProductInfo());
        }
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

    public SelenideElement getPlusButtonByProductTitle(String productTitle) {
        return $x(String.format(plusButtonByProductTitleXpath, productTitle));
    }

    public SelenideElement getMinusButtonByProductTitle(String productTitle) {
        return $x(String.format(minusButtonByProductTitleXpath, productTitle));
    }

    public SelenideElement getSubtotalByProductTitle(String productTitle) {
        return $x(String.format(subtotalByProductTitleXpath, productTitle));
    }

    public SelenideElement getQuantityByProductTitle(String productTitle){
        return $x(String.format(quantityInputInCartByProductTitleXpath, productTitle));
    }

    @Step("Click on Plus Button of selected product")
    public void clickPlusBtn(Product product) {
        SelenideElement plusButton = getPlusButtonByProductTitle(product.getTitle());
        plusButton.scrollIntoView(false).click();
        WaitUtils.waitForElementUpdate(getSubtotalByProductTitle(product.getTitle()),Constants.LONG_WAIT);
    }

    @Step("Click on Minus Button of selected product")
    public void clickMinusBtn(Product product) {
        SelenideElement minusButton = getMinusButtonByProductTitle(product.getTitle());
        minusButton.scrollIntoView(false).click();
        WaitUtils.waitForElementUpdate(getSubtotalByProductTitle(product.getTitle()),Constants.LONG_WAIT);
    }

    @Step("Set new Quantity value of selected product")
    public void setQuantity(Product product, int quantity){
        SelenideElement quantityField = getQuantityByProductTitle(product.getTitle());
        quantityField.clear();
        quantityField.setValue(String.valueOf(quantity));
    }

    @Step("Click on Uơdate Cart Button")
    public void clickOnUpdateCartBtn(){
        updateCartButton.scrollIntoView(false).click();
        WaitUtils.waitForAjaxComplete();
    }

    @Step("Verify Quanity of selected Product on Cart")
    public boolean verifyProductQuantityHasValue(Product product) {
        log.info("Selected product on Cart: " + product.getProductInfo());
        ElementUtils.refreshIfNotLoaded(getQuantityByProductTitle(product.getTitle()), Constants.MEDIUM_WAIT);
        return getQuantityByProductTitle(product.getTitle()).scrollIntoView(false).isDisplayed() &&
                getQuantityByProductTitle(product.getTitle()).scrollIntoView(false).getValue() != null &&
                !getQuantityByProductTitle(product.getTitle()).scrollIntoView(false).getValue().trim().isEmpty();
    }

    public Product updateProductAfterChangeQuantity(Product inputProduct){
        WaitUtils.waitForATime(Constants.SHORT_WAIT);
        int updatedQuantity = Integer.parseInt(getQuantityByProductTitle(inputProduct.getTitle()).scrollIntoView(false).getValue());
        String updatedSubtotal = StringUtils.getCleanText(getSubtotalByProductTitle(inputProduct.getTitle()).scrollIntoView(false));
        Product updatedProduct = new Product(inputProduct.getTitle(), inputProduct.getPrice(), updatedQuantity, updatedSubtotal);
        return updatedProduct;
    }

    private boolean checkQuantityAndSubtotal(Product updatedProduct, int expectedQuantity) {
        // Rule 1: Check Quantity
        boolean isQuantityCorrect = updatedProduct.getQuantity() == expectedQuantity;

        // Rule 2: Subtotal = Price * Quantity
        boolean isSubtotalCorrect = StringUtils.parsePrice(updatedProduct.getSubTotal())
                == StringUtils.parsePrice(updatedProduct.getPrice()) * updatedProduct.getQuantity();

        return isQuantityCorrect && isSubtotalCorrect;
    }

    @Step("Verify Quantity and Subtotal of Product after clicking Plus button")
    public boolean checkQuantityAndSubtotalAfterClickPlus(Product originProduct, Product updatedProduct) {
        log.info("Product after click Plus: " + updatedProduct.getProductInfo());
        int expectedQuantity = originProduct.getQuantity() + 1;
        return checkQuantityAndSubtotal(updatedProduct, expectedQuantity);
    }

    @Step("Verify Quantity and Subtotal of Product after clicking Minus button")
    public boolean checkQuantityAndSubtotalAfterClickMinus(Product originProduct, Product updatedProduct) {
        log.info("Product after click Minus: " + updatedProduct.getProductInfo());
        int expectedQuantity = originProduct.getQuantity() - 1;
        return checkQuantityAndSubtotal(updatedProduct, expectedQuantity);
    }

    @Step("Verify Quantity and Subtotal of Product after update Quantity value")
    public boolean checkQuantityAndSubtotalAfterEnterQuantity(Product updatedProduct, int enteredQuantity) {
        log.info("Product after changing Quantity: " + updatedProduct.getProductInfo());
        return checkQuantityAndSubtotal(updatedProduct, enteredQuantity);
    }

}
