package istad.co.darambbankingapi.features.user.dto;

import istad.co.darambbankingapi.domain.Account;

import java.time.LocalDate;
import java.util.List;

public record UserResponse(
        String uuid,
        String nationalCardId,
        String phoneNumber,
        String name,
        String profileImage,
        String gender,
        LocalDate dob
) {
}
