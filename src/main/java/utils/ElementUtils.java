package utils;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.refresh;

public class ElementUtils {
    public static void refreshIfNotLoaded(Object target, int timeoutInSeconds) {
        if (target instanceof SelenideElement) {
            SelenideElement element = (SelenideElement) target;
            if (!element.isDisplayed()) {
                refresh();
            }
            element.shouldBe(Condition.visible, Duration.ofSeconds(timeoutInSeconds));

        } else if (target instanceof ElementsCollection) {
            ElementsCollection elements = (ElementsCollection) target;
            if (elements.isEmpty()) {
                refresh();
            }
            elements.shouldBe(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(timeoutInSeconds));

        } else {
            throw new IllegalArgumentException("Unsupported target type: " + target.getClass());
        }
    }
}
