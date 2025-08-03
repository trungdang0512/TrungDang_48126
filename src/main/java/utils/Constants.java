package utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final Map<String, String> ConfigFiles = new HashMap<>();

    public static final String TA_URL = "https://demo.testarchitect.com/";
    public static final String VALID_ACCOUNT = "accountJSON_path";
    public static final String VALID_BILLING_DETAILS = "billingJSON_path";

    public static final int SHORT_WAIT = 5;
    public static final int MEDIUM_WAIT = 10;
    public static final int LONG_WAIT = 30;

    static {
        ConfigFiles.put(VALID_ACCOUNT, "src/test/resources/data/UserAccounts.json");
        ConfigFiles.put(VALID_BILLING_DETAILS,"src/test/resources/data/BillingInfo.json");
    }
}
