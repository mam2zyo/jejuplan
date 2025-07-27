package net.codecraft.jejutrip.tour.review.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.codecraft.jejutrip.account.user.domain.User;
import net.codecraft.jejutrip.tour.place.domain.Place;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(nullable = false)
    private String content;

    @Builder
    public Review(User user, Place place, String content) {
        this.user = user;
        this.place = place;
        this.content = content;
    }
}
