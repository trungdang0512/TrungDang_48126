package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import models.Review;
import utils.Constants;
import utils.StringUtils;
import utils.WaitUtils;

import static com.codeborne.selenide.Selenide.$x;

@Log4j
public class ProductDetailPage extends BasePage {
    SelenideElement reviewsTabLink = $x("//ul[@class='wc-tabs tabs-nav']//a[@id='tab_reviews']/span");
    SelenideElement reviewCommentTextBox = $x("//p[@class='comment-form-comment']/textarea[@id='comment']");
    SelenideElement submitReviewBtn = $x("//p[@class='form-submit']/input[@id='submit']");
    SelenideElement descriptionOfLastReview = $x("//ol[@class='commentlist']/li[last()]//div[@class='description']/p");
    SelenideElement ratingOfLastReview = $x("//ol[@class='commentlist']/li[last()]//div[@class='star-rating']");

    public static final String starByNumber = "//p[@class='stars']//a[@class='star-%s']";

    @Step("Click on REVIEWS tab")
    public void clickOnReviewsTabLink() {
        WaitUtils.waitForElementToBeVisible(reviewsTabLink, Constants.SHORT_WAIT);
        WaitUtils.waitUntilClickable(reviewsTabLink, Constants.SHORT_WAIT);
        reviewsTabLink.scrollIntoView(false).click();
    }

    public int getNumberOfReviews() {
        String reviewsText = reviewsTabLink.scrollIntoView(false).getText();
        int reviewsNumber = StringUtils.extractNumberFromString(reviewsText);
        log.info("Number of review: " + reviewsNumber);
        return reviewsNumber;
    }

    @Step("Verify number of reviews after submit")
    public boolean checkReviewNumberAfterSubmit(int beforeNumber, int afterNumber) {
        return afterNumber == beforeNumber + 1 ;
    }

    public void clickOnRating(Review review) {
        SelenideElement ratingStart = $x(String.format(starByNumber, String.valueOf(review.getRating())));
        ratingStart.scrollIntoView(false).click();
    }

    public void enterReviewContent(Review review) {
        reviewCommentTextBox.scrollIntoView(false).setValue(review.getContent());
    }

    public void clickOnSubmitBtn() {
        submitReviewBtn.scrollIntoView(false).click();
    }

    @Step("Submit A Review")
    public void submitAReview(Review reviewInfo) {
        log.info("Input Review info: " + reviewInfo.getReviewInfo());
        clickOnRating(reviewInfo);
        enterReviewContent(reviewInfo);
        clickOnSubmitBtn();
        WaitUtils.waitForAjaxComplete();
    }

    public int getRatingNumberFromLatestReview() {
        String ariaLabel = ratingOfLastReview.scrollIntoView(false).getAttribute("aria-label");
        int digits = StringUtils.extractFirstNumberFromString(ariaLabel);
        return digits;
    }

    public Review getLatestReview() {
        int ratingValue = getRatingNumberFromLatestReview();
        String description = descriptionOfLastReview.scrollIntoView(false).getAttribute("textContent");
        Review postedReview = new Review(ratingValue, description);
        log.info("Posted Review info: " + postedReview.getReviewInfo());
        return postedReview;
    }

    @Step("Verify posted review is correct")
    public boolean checkPostedReviewAreCorrect(Review inputReview, Review postedReview) {
        return postedReview.equals(inputReview);
    }
}
