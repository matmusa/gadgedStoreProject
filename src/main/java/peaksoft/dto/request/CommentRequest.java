package peaksoft.dto.request;

import lombok.Builder;

@Builder
public record CommentRequest(

         String comment
) {
}
