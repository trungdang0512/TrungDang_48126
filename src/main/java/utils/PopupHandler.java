package utils;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.UIAssertionError;
import lombok.extern.log4j.Log4j;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
@Log4j
public class PopupHandler {
    private static final SelenideElement popupMessage = $x("//div[contains(@class, 'pum-overlay') and contains(@style, 'display: block')]");
    private static final SelenideElement popupContainer = $("#pum-5700");
    private static final SelenideElement closeButton = popupContainer.$("button.pum-close.popmake-close");


    public static void closePopupIfPresent() {
        log.info("Checking for popup...");

        if (popupContainer.exists() && popupContainer.isDisplayed()) {
            log.info("Popup is visible");

            try {
                // Scroll to button to make sure it's visible
                closeButton.scrollIntoView(true);
                if (!closeButton.isDisplayed()) {
                    log.warn("Close button not visible, try JS click anyway");
                }
                Selenide.executeJavaScript("arguments[0].click();", closeButton);
                log.info("Popup closed via JS click");
                popupContainer.shouldBe(Condition.hidden, Duration.ofSeconds(5));
            } catch (Exception e) {
                log.error("Failed to close popup: " + e.getMessage());
            }
        } else {
            log.info("Popup not shown, skipping.");
        }
    }
}
