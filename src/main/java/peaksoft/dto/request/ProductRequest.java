package peaksoft.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import peaksoft.enums.Category;

import java.util.List;

public record ProductRequest(

        String name,
        String price,
        List<String> images,
        String characteristic,
        Category category
) {
}
