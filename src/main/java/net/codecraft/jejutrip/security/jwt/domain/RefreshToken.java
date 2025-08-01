package net.codecraft.jejutrip.security.jwt.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.codecraft.jejutrip.security.jwt.dto.Token;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long tokenId;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private String keyEmail;

    public static RefreshToken defaultRefreshToken() {
        return RefreshToken.builder()
                .tokenId(1L)
                .token(" ")
                .keyEmail(" ")
                .build();
    }

    public static RefreshToken createRefreshToken(Token token) {
        return RefreshToken.builder()
                .keyEmail(token.getKey())
                .token(token.getRefreshToken())
                .build();
    }
}
