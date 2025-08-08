package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.JsonUtils;

import static utils.Constants.ConfigFiles;
import static utils.Constants.VALID_ACCOUNT;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private String username;
    private String password;

    public static User getAccountFromJson(String key){
        return JsonUtils.to(ConfigFiles.get(VALID_ACCOUNT),key,User.class);
    }
}
