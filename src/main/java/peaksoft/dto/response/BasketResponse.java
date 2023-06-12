package peaksoft.dto.response;

import peaksoft.entity.User;

public record BasketResponse(
        Long id,
        User user
) {
}
