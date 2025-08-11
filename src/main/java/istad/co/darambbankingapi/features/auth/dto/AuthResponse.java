package istad.co.darambbankingapi.features.auth.dto;

public record AuthResponse(
        String type,
        String accessToken,
        String refreshToken
) {
}
