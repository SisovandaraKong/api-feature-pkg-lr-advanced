package istad.co.darambbankingapi.features.user.dto;


import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateProfileUser(

        String name,

        String profileImage,

        @Past(message = "Date of birth must be in the past")
        LocalDateTime dob, // date of birth

        String cityOrProvince,

        String khanOrDistrict,

        String sangkatOrCommune,

        String village,

        String street,

        String employeeType,

        String position,

        String companyName,

        String nameSourceOfIncome,

        @Positive
        BigDecimal monthlyIncomeRange

){}
