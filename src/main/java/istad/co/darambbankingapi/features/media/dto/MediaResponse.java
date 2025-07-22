package istad.co.darambbankingapi.features.media.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MediaResponse(
        String name, // unique
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String contentType, // PNG, JPEG, MP4
        String extension,
        String uri,         // full url
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long size
) {
}
