package istad.co.darambbankingapi.features.accountType.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record AccountTypeRequest(
        @NotBlank(message = "Name is required")
        String name,
        String description
) {
}
