package utils;

import com.codeborne.selenide.SelenideElement;

public class StringUtils {
    public static double parsePrice(String priceText) {
        if (priceText == null || priceText.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(priceText.replaceAll("[^\\d.]+", ""));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse price from string: " + priceText, e);
        }
    }

    public static String getCleanText(SelenideElement element) {
        String raw = element.getAttribute("textContent");
        return raw == null ? "" : raw.replaceAll("\\s+", " ").trim();
    }
}
