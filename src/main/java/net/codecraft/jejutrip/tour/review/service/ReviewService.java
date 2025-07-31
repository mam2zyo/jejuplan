package net.codecraft.jejutrip.tour.review.service;

import lombok.RequiredArgsConstructor;
import net.codecraft.jejutrip.account.user.domain.User;
import net.codecraft.jejutrip.account.user.repository.UserRepository;
import net.codecraft.jejutrip.tour.place.domain.Place;
import net.codecraft.jejutrip.tour.place.repository.PlaceRepository;
import net.codecraft.jejutrip.tour.review.domain.Review;
import net.codecraft.jejutrip.tour.review.dto.ReviewRequest;
import net.codecraft.jejutrip.tour.review.dto.ReviewResponse;
import net.codecraft.jejutrip.tour.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public Review createReview(Long placeId, ReviewRequest request, String email) {
        User reviewer = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. email=" + email));
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소를 찾을 수 없습니다. id=" + placeId));

        Review review = Review.builder()
                .reviewer(reviewer)
                .place(place)
                .rating(request.getRating())
                .content(request.getContent())
                .build();

        reviewRepository.save(review);

        return review;
    }

    public ReviewResponse updateReview(Long reviewId, ReviewRequest request, String email) {
        return null;
    }
}
