package peaksoft.dto.request;

import lombok.Builder;
import peaksoft.entity.User;
@Builder
public record BasketRequest(

        User user
) {
}
