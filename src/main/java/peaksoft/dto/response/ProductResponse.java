package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.enums.Category;

import java.util.List;
@Builder
public record ProductResponse(
        Long id,
        String name,
        String price,
        List<String> images,
        String characteristic,

        Category category
) {
}
