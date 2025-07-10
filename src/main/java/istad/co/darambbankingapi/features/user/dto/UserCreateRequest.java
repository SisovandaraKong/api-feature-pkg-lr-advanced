package istad.co.darambbankingapi.features.user.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserCreateRequest(
        @NotNull
        @Size(max = 20)
        String nationalCardId,
        @Min(4)
        @Max(4)
        @Positive
        Integer pin,
        @NotBlank
        @Size(min = 8, max = 10)
        String phoneNumber,
        @NotBlank
        String password,
        @NotBlank
        @Size(max = 40)
        String name,
        @NotBlank
        @Size(max = 6)
        String gender,
        @NotNull
        LocalDate dob,
        @Size(max = 20)
        String studentCardId
) {
}
