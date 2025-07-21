package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import models.Product;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
@Log4j
public class OrderStatusPage extends BasePage{
    private final ElementsCollection allProductsTitleOnReceipt = $$x("//tr[contains(@class, 'order_item')]//td[contains(@class, 'product-name')]//a");
    private final ElementsCollection allProductsQuantityOnReceipt = $$x("//tr[contains(@class, 'order_item')]//td[contains(@class, 'product-name')]//strong");
    private final ElementsCollection allProductsSubTotalOnReceipt = $$x("//tr[contains(@class, 'order_item')]/td[contains(@class, 'product-total')]//bdi");

    private final SelenideElement orderConfirmationMessage = $x("//p[contains(@class, 'woocommerce-notice--success')]");
    private final SelenideElement orderNumber = $x("//li[contains(@class, 'woocommerce-order-overview__order')]/strong");

    @Step("Check Order Status Page displays")
    public boolean isOrderStatusPage() {
        return WebDriverRunner.getWebDriver().getCurrentUrl().contains("/order-received/");
    }

    private Product getProductFromReceiptByIndex(int index) {
        String titleText = allProductsTitleOnReceipt.get(index).getText();

        String quantityText = allProductsQuantityOnReceipt.get(index).getText().replaceAll("[^0-9]", "");

        String subTotatlText = allProductsSubTotalOnReceipt.get(index).getAttribute("textContent");

        return new Product(titleText,quantityText,subTotatlText);
    }

    public List<Product> getAllProductsOnReceipt(){
        List<Product> productList = new ArrayList<>();

        int count = Math.min(allProductsTitleOnReceipt.size(), allProductsQuantityOnReceipt.size());

        for (int i = 0; i < count; i++) {
            productList.add(getProductFromReceiptByIndex(i));
        }
        return productList;
    }

    @Step("Check if selected product displayed on Receipt")
    public boolean checkSelectedProductDisplayedOnReceipt(Product selectedProduct, List<Product> productsOnReceipt){
        log.info("Purchased products list: {}"+ productsOnReceipt);
        return productsOnReceipt.stream()
                .anyMatch(product -> product.getTitle().equals(selectedProduct.getTitle()));
    }

}
