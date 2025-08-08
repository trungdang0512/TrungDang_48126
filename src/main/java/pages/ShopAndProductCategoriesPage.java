package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import models.Product;
import utils.Constants;
import utils.WaitUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;

@Log4j
public class ShopAndProductCategoriesPage extends BasePage{

    private final SelenideElement listViewSwitchBtn = $x("//div[@class='view-switcher']/div[contains(@class,'switch-list')]");
    private final SelenideElement gridViewSwitchBtn = $x("//div[@class='view-switcher']/div[contains(@class,'switch-grid')]");

    private final ElementsCollection allProductsTitleOnPage = $$x("//h2[@class='product-title']/a");
    private final ElementsCollection allProductsPriceOnPage = $$x("//span[@class='price']//bdi[not(ancestor::del)]");

    private final SelenideElement sortSelection = $x("//select[@name='orderby']");

    private final String addToCartLink = "//div[@class='text-center product-details']/a[@data-product_name='%s']";

    @Step("Switch view to list")
    public void switchViewToList(){
        actions().moveToElement(listViewSwitchBtn).click().perform();
        WaitUtils.waitForUrlChange(Constants.LONG_WAIT);
    }

    @Step("Check items displayed as grid view")
    public boolean checkGridView(){
        String classAttr = gridViewSwitchBtn.getAttribute("class");
        return Objects.requireNonNull(classAttr).contains("switcher-active");
    }

    @Step("Check items displayed as list view")
    public boolean checkListView(){
        String classAttr = listViewSwitchBtn.getAttribute("class");
        return Objects.requireNonNull(classAttr).contains("switcher-active");
    }

    private Product getProductFromPageByIndex(int index) {
        String title = allProductsTitleOnPage.get(index).getAttribute("innerHTML").trim();

        String price = allProductsPriceOnPage.get(index).getText();

        return new Product(title, price);
    }

    public List<Product> getAllProducts() {

        int count = Math.min(allProductsTitleOnPage.size(), allProductsPriceOnPage.size());//make sure 2 size are equal
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            products.add(getProductFromPageByIndex(i));
        }
        log.info("Products list on Page: {}" + products);
        return products;
    }

    @Step("Add item to cart")
    public void addItemToCart(Product product){
        SelenideElement addToCartLinkOfSelectedItem = $x(String.format(addToCartLink, product.getTitle()));
        WaitUtils.waitForElementToBeVisible(addToCartLinkOfSelectedItem,Constants.SHORT_WAIT);
        WaitUtils.waitUntilClickable(addToCartLinkOfSelectedItem, Constants.SHORT_WAIT);
        addToCartLinkOfSelectedItem.scrollIntoView(false).click();
    }

    @Step("Add multiple items to cart")
    public void addMutipleItemsToCart(List<Product> products) {
        log.info("Selected Products: {}" + products.toString());
        for (Product product : products) {
            addItemToCart(product);
        }
    }

}
