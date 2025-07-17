package istad.co.darambbankingapi.features.user.dto;

import java.time.LocalDate;

public record UserSnippetsResponse(
        String uuid,
        String phoneNumber,
        String name,
        String profileImage
) {
}
