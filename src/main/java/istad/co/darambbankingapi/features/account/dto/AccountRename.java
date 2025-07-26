package istad.co.darambbankingapi.features.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountRename(
        @NotBlank(message = "New name is required")
                @Size(message = "New name is equal or less than 100 chars")
        String newName
) {
}
