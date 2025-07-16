package istad.co.darambbankingapi.features.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangePasswordUser(

        @NotBlank(message = "Old password is required")
        String oldPassword,

        @NotBlank(message = "Please enter new password")
        String newPassword,

        @NotBlank(message = "Please enter confirmed password")
        String confirmedPassword

) {
}
