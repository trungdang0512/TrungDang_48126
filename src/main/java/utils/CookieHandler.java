package utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
@Log4j
public class CookieHandler {
    private static final SelenideElement cookieNotice = $("#cookie-notice");
    private static final SelenideElement acceptButton = $("#cn-accept-cookie");

    public static void acceptCookieIfVisible() {
        log.info("Checking for cookie notice...");

        if (cookieNotice.exists() && cookieNotice.isDisplayed()) {
            log.info("Cookie notice is visible. Accepting...");

            try {
                acceptButton.scrollIntoView(true);
                acceptButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
                acceptButton.click();
                cookieNotice.shouldBe(Condition.hidden, Duration.ofSeconds(5));
                log.info("Cookie notice dismissed.");
            } catch (Exception e) {
                log.warn("Failed to dismiss cookie notice: " + e.getMessage());
            }
        } else {
            log.info("No cookie notice present.");
        }
    }
}
