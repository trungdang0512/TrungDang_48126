package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import utils.JsonUtils;

import static utils.Constants.ConfigFiles;
import static utils.Constants.VALID_BILLING_DETAILS;

@Getter
@Setter
@AllArgsConstructor
public class BillingInfo {
    private String firstName;
    private String lastName;
    private String companyName;
    private String country;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postCode;
    private String phone;
    private String email;
    private String orderComments;

    public static BillingInfo getBillingDetailsFromJson(String key) {
        return JsonUtils.to(ConfigFiles.get(VALID_BILLING_DETAILS), key, BillingInfo.class);
    }
}
