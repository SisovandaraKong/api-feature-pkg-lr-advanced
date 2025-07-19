package istad.co.darambbankingapi.features.media.dto;

import lombok.Builder;

@Builder
public record MediaResponse(
        String name, // unique
        String contentType, // PNG, JPEG, MP4
        String extension,
        String uri,         // full url
        Long size
) {
}
