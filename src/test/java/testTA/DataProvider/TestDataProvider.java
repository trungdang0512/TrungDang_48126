package testTA.DataProvider;

import models.BillingInfo;
import models.User;
import org.testng.annotations.DataProvider;

public class TestDataProvider {
    @DataProvider(name = "validAccount")
    public static Object[][] provideValidAccount() {
        User validUser = User.getAccountFromJson("validAccount");
        return new Object[][]{
                {validUser}
        };
    }

    @DataProvider(name = "validBilling")
    public static Object[][] provideValidBillingDetails() {
        BillingInfo billingInfo = BillingInfo.getBillingDetailsFromJson("validBilling");
        return new Object[][]{
                {billingInfo}
        };
    }

    @DataProvider(name = "validAccountAndBilling")
    public static Object[][] provideValidAccountAndBillingDetails() {
        User validUser = User.getAccountFromJson("validAccount");
        BillingInfo billingInfo = BillingInfo.getBillingDetailsFromJson("validBilling");
        return new Object[][]{
                {validUser, billingInfo}
        };
    }

    @DataProvider(name = "validAccountAndInvalidBilling")
    public static Object[][] provideValidAccountAndInvalidBillingDetails() {
        User validUser = User.getAccountFromJson("validAccount");
        BillingInfo missingInfoBilling = BillingInfo.getBillingDetailsFromJson("missingInfoBilling");
        return new Object[][]{
                {validUser, missingInfoBilling}
        };
    }
}
