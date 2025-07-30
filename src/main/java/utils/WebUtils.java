package utils;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class WebUtils {
    public static void scrollToTop() {
        executeJavaScript("window.scrollTo(0, 0);");
    }

}
