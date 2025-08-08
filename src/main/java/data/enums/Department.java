package data.enums;

import lombok.Getter;

@Getter
public enum Department {
    AUTOMOBILES_MOTOCICLES("Automobiles & Motorcycles"),
    CAR_ELECTRONICS("Car Electronics"),
    MOBILE_PHONE_ACCESSORIES("Mobile Phone Accessories"),
    COMPUTER_OFFICE("Computer & Office"),
    TABLET_ACCESSORIES("Tablet Accessories"),
    CONSUMER_ELECTRONIC("Consumer Electronics"),
    ELECTRONIC_COMPONENT("Electronic Components & Supplies"),
    PHONE_TELECOMMUNICATIONS("Phones & Telecommunications"),
    WATCHES("Watches");

    private String value;

    Department(String value) {
        this.value = value;
    }
}
