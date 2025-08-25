package testTA;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.ShopPage;

public class DemoTest extends TATestBase {
    private final MainPage mainPage = new MainPage();
    private final ShopPage shopPage = new ShopPage();

    @Test(description = "Verify the TA page opened correctly")
    public void openPageTest(){
        mainPage.goToShopPage();
        Assert.assertEquals(shopPage.getCurrentUrl(),"https://demo.testarchitect.com/shop/");
    }
}
