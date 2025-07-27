package net.codecraft.jejutrip.tour.review.controller;

import lombok.RequiredArgsConstructor;
import net.codecraft.jejutrip.tour.review.dto.ReviewRequest;
import net.codecraft.jejutrip.tour.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/places/{placeId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 특정 장소에 리뷰 작성
    @PostMapping
    public ResponseEntity<Void> createReview(@PathVariable Long placeId,
                                             @RequestBody ReviewRequest requestDto,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = userDetails.getUsername();

        Long reviewId = reviewService.createReview(placeId, requestDto, email);

        return ResponseEntity.created(URI.create("/api/places/" + placeId + "/reviews/" + reviewId)).build();
    }
}
