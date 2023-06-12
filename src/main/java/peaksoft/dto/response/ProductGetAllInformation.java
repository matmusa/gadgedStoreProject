package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.enums.Category;

import java.util.List;
@Builder
public record ProductGetAllInformation(
        Long id,
        String name,
        String price,
        List<String> images,
        String characteristic,
        Boolean isFavorite,
        Category category,
        List<String> comments,
        int favorites


) {


}
