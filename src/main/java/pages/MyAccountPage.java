package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MyAccountPage extends BasePage {
    private final SelenideElement ordersLinkInLeftNavigation = $x("//li[contains(@class, 'woocommerce-MyAccount-navigation-link--orders')]");

    private final ElementsCollection allOrderNumberOnTable = $$x("//td[contains(@class,'woocommerce-orders-table__cell-order-number')]//a");

    public void goToOrdersPage() {
        ordersLinkInLeftNavigation.scrollIntoView(false).click();
    }

    public List<String> getAllOrderNumberOnPage() {
        return allOrderNumberOnTable.stream()
                .map(SelenideElement::getText)
                .map(text -> text.replace("#", "").trim())
                .collect(Collectors.toList());
    }

    public boolean areNewOrdersDisplayed(List<String> newOrders) {
        List<String> allOrderNumbers = getAllOrderNumberOnPage();

        for (String newOrder : newOrders) {
            if (!allOrderNumbers.contains(newOrder)) {
                return false;
            }
        }
        return true;
    }
}
