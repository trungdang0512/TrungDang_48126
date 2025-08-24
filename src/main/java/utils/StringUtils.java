package utils;

import com.codeborne.selenide.SelenideElement;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static int generateRandomNumber(int bound) {
        return new Random().nextInt(bound) + 1; // 1 → bound
    }

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

    public static int extractNumberFromString(String text) {
        return Integer.parseInt(text.replaceAll("\\D+", ""));
    }

    public static int extractFirstNumberFromString(String text) {
        if (text == null) return -1;
        Matcher m = Pattern.compile("\\d+").matcher(text);
        if (m.find()) {
            return Integer.parseInt(m.group()); //get the firt number
        }
        return -1;
    }
}
