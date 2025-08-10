package testTA;

import data.enums.Department;
import data.enums.ProductsSortingOptions;
import jdk.jfr.Description;
import models.BillingInfo;
import models.Product;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testTA.DataProvider.TestDataProvider;
import utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class PurchaseItemsTest extends TATestBase {

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
        mainPage.goToLoginPage();
        loginPage.loginWithAccount(validUser);

        loginPage.selectDepartment(Department.ELECTRONIC_COMPONENT);

        softAssert.assertTrue(shopAndProductCategoriesPage.checkGridView(), "The products are not displayed as grid view");

        shopAndProductCategoriesPage.switchViewToList();

        softAssert.assertTrue(shopAndProductCategoriesPage.checkListView(), "The products are not displayed as list view");

        products = shopAndProductCategoriesPage.getAllProducts();
        selectedRandomProduct = ListUtils.getRandomElement(products);

        shopAndProductCategoriesPage.addItemToCart(selectedRandomProduct);

        shopAndProductCategoriesPage.goToCartPage();

        productsOnCart = cartPage.getAllProductsOnCart();
        softAssert.assertTrue(cartPage.checkAddedProductDisplayedOnCart(selectedRandomProduct, productsOnCart), "Selected product is not displayed on Cart page");

        cartPage.clickOnCheckoutBtn();

        softAssert.assertTrue(checkOutPage.isCheckoutPage(), "Checkout Page doesn't  display!!");

        productsOnCheckOutPage = checkOutPage.getAllProductsOnOrder();
        softAssert.assertTrue(checkOutPage.checkSelectedProductDisplayedOnOrder(selectedRandomProduct, productsOnCheckOutPage), "Selected product is not displayed on Check out page");

        checkOutPage.enterBillingDetails(validBillingInfo);

        checkOutPage.clickOnPlaceOrderBtnAndWait();

        softAssert.assertTrue(orderStatusPage.isOrderStatusPage(), "Order Status page doesn't display!!!");

        productsOnReceipt = orderStatusPage.getAllProductsOnReceipt();
        softAssert.assertTrue(orderStatusPage.checkSelectedProductDisplayedOnReceipt(selectedRandomProduct, productsOnReceipt), "Selected product is not displayed on Order Status page");
        softAssert.assertAll();
    }

    @Test(dataProvider = "validAccountAndBilling", dataProviderClass = TestDataProvider.class)
    @Description("TC02: Verify users can buy multiple item successfully")
    public void TC_02(User validUser, BillingInfo validBillingInfo) {
        mainPage.goToLoginPage();
        loginPage.loginWithAccount(validUser);

        mainPage.goToShopPage();

        products = shopAndProductCategoriesPage.getAllProducts();
        selectedProducsList = ListUtils.getRandomElements(products);
        shopAndProductCategoriesPage.addMutipleItemsToCart(selectedProducsList);

        shopAndProductCategoriesPage.goToCartPage();
        productsOnCart = cartPage.getAllProductsOnCart();
        softAssert.assertTrue(cartPage.checkAddedProductsDisplayedOnCart(selectedProducsList, productsOnCart), "Selected products are not displayed on Cart page");

        cartPage.clickOnCheckoutBtn();
        checkOutPage.enterBillingDetails(validBillingInfo);
        checkOutPage.clickOnPlaceOrderBtnAndWait();

        softAssert.assertTrue(orderStatusPage.checkConfirmationMessageDisplayed(), "Order Confirmation Message is not displayed");
        productsOnReceipt = orderStatusPage.getAllProductsOnReceipt();
        softAssert.assertTrue(orderStatusPage.checkSelectedProductsDisplayedOnReceipt(selectedProducsList, productsOnReceipt), "Selected products are not displayed on Order Status page");
        softAssert.assertAll();
    }

    @Test(dataProvider = "validAccountAndBilling", dataProviderClass = TestDataProvider.class)
    @Description("TC03: Verify users can buy an item using different payment methods (all payment methods")
    public void TC_03(User validUser, BillingInfo validBillingInfo) {
        mainPage.goToLoginPage();
        loginPage.loginWithAccount(validUser);

        mainPage.goToShopPage();

        products = shopAndProductCategoriesPage.getAllProducts();
        selectedRandomProduct = ListUtils.getRandomElement(products);
        shopAndProductCategoriesPage.addItemToCart(selectedRandomProduct);
        shopAndProductCategoriesPage.goToCartPage();

        cartPage.clickOnCheckoutBtn();

        checkOutPage.enterBillingDetails(validBillingInfo);
        checkOutPage.selectCheckPaymentMethod();

        checkOutPage.clickOnPlaceOrderBtnAndWait();

        softAssert.assertTrue(orderStatusPage.checkConfirmationMessageDisplayed(), "Order Confirmation Message is not displayed");
        productsOnReceipt = orderStatusPage.getAllProductsOnReceipt();
        softAssert.assertTrue(orderStatusPage.checkSelectedProductDisplayedOnReceipt(selectedRandomProduct, productsOnReceipt), "Selected products are not displayed on Order Status page");

        //9. Repeat Step 3-8 with another payment method (Cash on delivery)
        mainPage.goToShopPage();

        products = shopAndProductCategoriesPage.getAllProducts();
        selectedRandomProduct = ListUtils.getRandomElement(products);
        shopAndProductCategoriesPage.addItemToCart(selectedRandomProduct);
        shopAndProductCategoriesPage.goToCartPage();

        cartPage.clickOnCheckoutBtn();

        checkOutPage.enterBillingDetails(validBillingInfo);
        checkOutPage.selectCODPaymentMethod();

        checkOutPage.clickOnPlaceOrderBtnAndWait();

        softAssert.assertTrue(orderStatusPage.checkConfirmationMessageDisplayed(), "Order Confirmation Message is not displayed");
        productsOnReceipt = orderStatusPage.getAllProductsOnReceipt();
        softAssert.assertTrue(orderStatusPage.checkSelectedProductDisplayedOnReceipt(selectedRandomProduct, productsOnReceipt), "Selected products are not displayed on Order Status page");
        softAssert.assertAll();
    }

    @Test(dataProvider = "validAccount", dataProviderClass = TestDataProvider.class)
    @Description("TC_014 Verify users can sort items by price")
    public void TC_04(User validUser) {
        mainPage.goToLoginPage();
        loginPage.loginWithAccount(validUser);

        mainPage.goToShopPage();
        shopAndProductCategoriesPage.switchViewToList();

        shopAndProductCategoriesPage.selectSortOption(ProductsSortingOptions.SORT_BY_PRICE_LOW_TO_HIGH);
        products = shopAndProductCategoriesPage.getAllProducts();

        softAssert.assertTrue(shopAndProductCategoriesPage.isProductPriceSortedAscending(products));

        shopAndProductCategoriesPage.selectSortOption(ProductsSortingOptions.SORT_BY_PRICE_HIGH_TO_LOW);
        products = shopAndProductCategoriesPage.getAllProducts();

        softAssert.assertTrue(shopAndProductCategoriesPage.isProductPriceSortedDescending(products));
        softAssert.assertAll();
    }

    @Test(dataProvider = "validAccountAndBilling", dataProviderClass = TestDataProvider.class)
    @Description("TC05: Verify orders appear in order history")
    public void TC_05(User validUser, BillingInfo validBillingInfo) {
        //Pre-condition: User has placed 02 orders
        mainPage.goToLoginPage();
        loginPage.loginWithAccount(validUser);

        mainPage.goToShopPage();

        products = shopAndProductCategoriesPage.getAllProducts();
        selectedRandomProduct = ListUtils.getRandomElement(products);
        shopAndProductCategoriesPage.addItemToCart(selectedRandomProduct);
        shopAndProductCategoriesPage.goToCartPage();

        cartPage.clickOnCheckoutBtn();
        checkOutPage.enterBillingDetails(validBillingInfo);
        checkOutPage.clickOnPlaceOrderBtnAndWait();

        newOrderNumbers.add(orderStatusPage.getOrderNumber());

        mainPage.goToShopPage();

        products = shopAndProductCategoriesPage.getAllProducts();
        selectedRandomProduct = ListUtils.getRandomElement(products);
        shopAndProductCategoriesPage.addItemToCart(selectedRandomProduct);
        shopAndProductCategoriesPage.goToCartPage();

        cartPage.clickOnCheckoutBtn();
        checkOutPage.enterBillingDetails(validBillingInfo);
        checkOutPage.clickOnPlaceOrderBtnAndWait();

        newOrderNumbers.add(orderStatusPage.getOrderNumber());

        //Start Step 1: Go to My Account page
        checkOutPage.goToMyAccountPage();

        myAccountPage.goToOrdersPage();

        Assert.assertTrue(myAccountPage.areNewOrdersDisplayed(newOrderNumbers));
    }

    @Test(dataProvider = "validBilling", dataProviderClass = TestDataProvider.class)
    @Description("TC06: Verify users try to buy an item without logging in (As a guest)")
    public void TC_06(BillingInfo validBillingInfo) {
        mainPage.goToShopPage();

        products = shopAndProductCategoriesPage.getAllProducts();
        selectedRandomProduct = ListUtils.getRandomElement(products);
        shopAndProductCategoriesPage.addItemToCart(selectedRandomProduct);

        shopAndProductCategoriesPage.goToCartPage();

        cartPage.clickOnCheckoutBtn();
        checkOutPage.enterBillingDetails(validBillingInfo);
        checkOutPage.clickOnPlaceOrderBtnAndWait();

        Assert.assertTrue(orderStatusPage.checkConfirmationMessageDisplayed(),"Order Confirmation Message is not displayed");
    }
}
