package istad.co.darambbankingapi.features.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserProfileImageRequest(
        @NotBlank(message = "Media name is required")
        String mediaName
) {
}
