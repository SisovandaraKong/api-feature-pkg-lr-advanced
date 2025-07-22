package istad.co.darambbankingapi.base;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasedError {
    // Request entity too large, bad request,...
    // For some company can custom this status code
    private String code;

    // Detail error for user
    private String description;

}
