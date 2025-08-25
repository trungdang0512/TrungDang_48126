package utils;

import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class WebDriverUtils {
    public static void scrollToTop() {
        executeJavaScript("window.scrollTo(0, 0);");
    }

    public static String getCurrentUrl() {
        return WebDriverRunner.getWebDriver().getCurrentUrl();
    }

    public static boolean isUrlContains(String partialUrl) {
        return getCurrentUrl().contains(partialUrl);
    }

    public static String getPageTitle() {
        return WebDriverRunner.getWebDriver().getTitle();
    }

    public static boolean isTitleContains(String partialTitle) {
        return getPageTitle().contains(partialTitle);
    }
}
