package utils;

import com.codeborne.selenide.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class WaitUtils {
    public static void waitForPageLoaded() {
        waitForPageLoaded(20);
    }

    public static void waitForPageLoaded(int timeoutSeconds) {
        WebDriver driver = getWebDriver();
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete")
        );
    }

    public static void waitUntilClickable(SelenideElement element, int timeoutInSeconds) {
        Wait<WebDriver> wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(timeoutInSeconds));
        wait.until(driver -> {
            try {
                return element.isDisplayed() && element.isEnabled();
            } catch (Exception e) {
                return false;
            }
        });
    }

    public static void waitForElementToBeVisible(SelenideElement element, int timeoutInSeconds) {
        WebDriver driver = getWebDriver();
        SelenideWait wait = new SelenideWait(driver, timeoutInSeconds * 1000L, 500); // timeout ms, polling ms
        wait.until(driver1 -> {
            try {
                return element.isDisplayed();
            } catch (Exception e) {
                return false;
            }
        });
    }

    public static void waitForElementToDisappear(SelenideElement element, int timeoutInSeconds) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        SelenideWait wait = new SelenideWait(driver, timeoutInSeconds * 1000L, 200);

        wait.until(d -> {
            try {
                return !element.isDisplayed();
            } catch (Exception e) {
                return true;
            }
        });
    }

    public static void waitForUrlChange(int timeoutInSeconds){
        String currentUrl = WebDriverRunner.url();
        Selenide.Wait().withTimeout(Duration.ofSeconds(timeoutInSeconds)).until(driver ->
                !WebDriverRunner.url().equals(currentUrl)
        );
    }

    public static void waitUntilTextsChange(ElementsCollection elements,
                                            List<String> previousTexts,
                                            int timeoutInSeconds) {
        Selenide.Wait().withTimeout(Duration.ofSeconds(timeoutInSeconds)).until(driver -> {
            List<String> currentTexts = elements.texts();
            return !currentTexts.equals(previousTexts);
        });
    }
}
