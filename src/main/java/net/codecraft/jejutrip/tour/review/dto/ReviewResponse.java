package net.codecraft.jejutrip.tour.review.dto;

import lombok.Getter;
import net.codecraft.jejutrip.tour.review.domain.Review;

@Getter
public class ReviewResponse {
    private final Long id;
    private final String content;
    private final String username; // 작성자 이름

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.username = review.getUser().getUsername();
    }
}