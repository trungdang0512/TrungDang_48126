package data.enums;

import lombok.Getter;

@Getter
public enum ProductsSortingOptions {
    DEFAULT_SORTING("menu_order", "Default sorting"),
    SORT_BY_POPULARITY("popularity", "Sort by popularity"),
    SORT_BY_AVERAGE_RATING("rating", "Sort by average rating"),
    SORT_BY_LATEST("date", "Sort by latest"),
    SORT_BY_PRICE_LOW_TO_HIGH("price", "Sort by price: low to high"),
    SORT_BY_PRICE_HIGH_TO_LOW("price-desc", "Sort by price: high to low");

    private final String value;
    private final String displayName;

    ProductsSortingOptions(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public String value() {
        return value;
    }

    public String displayName() {
        return displayName;
    }

}
