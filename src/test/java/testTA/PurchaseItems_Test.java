package testTA;

import data.enums.Departments;
import jdk.jfr.Description;
import models.BillingInfo;
import models.Product;
import models.User;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import testTA.DataProvider.TestDataProvider;
import utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class PurchaseItems_Test extends TA_TestBase {
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final ShopAndProductCategoriesPage shopAndProductCategoriesPage = new ShopAndProductCategoriesPage();
    private final CartPage cartPage = new CartPage();
    private final CheckOutPage checkOutPage = new CheckOutPage();
    private final OrderStatusPage orderStatusPage = new OrderStatusPage();

    List<Product> selectedProducsList;
    List<Product> products;
    List<Product> productsOnCart;
    List<Product> productsOnCheckOutPage;
    List<Product> productsOnReceipt;
    List<String> newOrderNumbers = new ArrayList<>();
    Product selectedRandomProduct;
    SoftAssert softAssert = new SoftAssert();

    @Test(dataProvider = "validAccountAndBilling", dataProviderClass = TestDataProvider.class)
    @Description("TC_01: Verify users can buy an item successfully")
    public void TC_01(User validUser, BillingInfo validBillingInfo) {
        //2. Login with valid credentials
        mainPage.goToLoginPage();
        loginPage.loginWithAccount(validUser);

        //3. Navigate to All departments section
        //4. Select Electronic Components & Supplies
        loginPage.selectDepartment(Departments.ELECTRONIC_COMPONENT);

        //5. Verify the items should be displayed as a grid
        softAssert.assertTrue(shopAndProductCategoriesPage.checkGridView(), "The products are not displayed as grid view");
        //6. Switch view to list
        shopAndProductCategoriesPage.switchViewToList();
        //7. Verify the items should be displayed as a list
        softAssert.assertTrue(shopAndProductCategoriesPage.checkListView(), "The products are not displayed as list view");
        //8. Select any item randomly to purchase
        products = shopAndProductCategoriesPage.getAllProducts();
        selectedRandomProduct = ListUtils.getRandomElement(products);
        //9. Click 'Add to Cart'
        shopAndProductCategoriesPage.addItemToCart(selectedRandomProduct);
        //10. Go to the cart
        shopAndProductCategoriesPage.goToCartPage();
        //11. Verify item details in mini content
        productsOnCart = cartPage.getAllProductsOnCart();
        softAssert.assertTrue(cartPage.checkAddedProductDisplayedOnCart(selectedRandomProduct, productsOnCart), "Selected product is not displayed on Cart page");
        //12. Click on Checkout
        cartPage.clickOnCheckoutBtn();
        //13. Verify Checkout page displays
        softAssert.assertTrue(checkOutPage.isCheckoutPage(),"Checkout Page doesn't  display!!");
        //14. Verify item details in order
        productsOnCheckOutPage = checkOutPage.getAllProductsOnOrder();
        softAssert.assertTrue(checkOutPage.checkSelectedProductDisplayedOnOrder(selectedRandomProduct, productsOnCheckOutPage), "Selected product is not displayed on Check out page");
        //15. Fill the billing details with default payment method
        checkOutPage.enterBillingDetails(validBillingInfo);
        //16. Click on PLACE ORDER
        checkOutPage.clickOnPlaceOrderBtnAndWait();
        //17. Verify Order status page displays
        softAssert.assertTrue(orderStatusPage.isOrderStatusPage(),"Order Status page doesn't display!!!");
        //18. Verify the Order details with billing and item information
        productsOnReceipt = orderStatusPage.getAllProductsOnReceipt();
        softAssert.assertTrue(orderStatusPage.checkSelectedProductDisplayedOnReceipt(selectedRandomProduct, productsOnReceipt),"Selected product is not displayed on Order Status page");
        softAssert.assertAll();

    }
}
