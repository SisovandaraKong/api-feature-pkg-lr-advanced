package istad.co.darambbankingapi.base;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasedResponse<T> {
    private T payload;
}
