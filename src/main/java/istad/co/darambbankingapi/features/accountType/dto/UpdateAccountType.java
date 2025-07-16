package istad.co.darambbankingapi.features.accountType.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateAccountType(
        @NotBlank(message = "Name is required")
        String name,
        String description
) {
}
