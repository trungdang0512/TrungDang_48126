package models;

import com.codeborne.selenide.SelenideElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.JsonUtils;
import utils.LoremIpsumGenerator;

import java.util.Objects;
import java.util.Random;

import static utils.Constants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private int rating;
    private String content;

    public String getReviewInfo() {
        return String.format(
                "Product [Rating='%s', Content='%s']",
                rating,
                content
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return rating == review.rating && Objects.equals(content, review.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, content);
    }
}
