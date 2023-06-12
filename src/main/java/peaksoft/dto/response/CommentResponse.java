package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record CommentResponse(
        Long id,
        String comment
) {
}
