package istad.co.darambbankingapi.features.user.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserCreateRequest(
        @NotBlank
        @Size(max = 20)
        String nationalCardId,
        @NotNull
        @Positive
                @Max(9999)
        Integer pin,
        @NotBlank
        @Size(max = 20)
        String phoneNumber,
        @NotBlank
        String password,
        @NotBlank
        String confirmedPassword,
        @NotBlank
        @Size(max = 40)
        String name,
        @NotBlank
        @Size(max = 6)
        String gender,
        LocalDate dob,
        @Size(max = 20)
        String studentCardId
) {
}
