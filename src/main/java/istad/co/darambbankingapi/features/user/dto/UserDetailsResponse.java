package istad.co.darambbankingapi.features.user.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record UserDetailsResponse(
        String uuid,
        String name,
        String phoneNumber,
        String nationalCardId,
        String profileImage,
        String gender,
        LocalDate dob,
        String cityOrProvince,
        String khanOrDistrict,
        String sangkatOrCommune,
        String village,
        String street,
        String employeeType,
        String position,
        String companyName,
        String nameSourceOfIncome,
        BigDecimal monthlyIncomeRange,
        Boolean isStudent,
        Boolean isBlocked,
        String oneSignalId,
        List<String> roles,
        LocalDateTime createdAt
) {
}
