package utils;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    public static void handleAlert(boolean accept, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(timeoutSeconds));
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            if (accept) {
                alert.accept();
            } else {
                alert.dismiss();
            }
        } catch (NoAlertPresentException e) {
            throw new RuntimeException("No alert was present within timeout", e);
        }
    }

    public static void handleAlert(boolean accept) {
        handleAlert(accept, 5);
    }
}
