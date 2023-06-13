package peaksoft.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record AllProductInformationResponse(

        List<ProductResponse> productResponseList,
        BigDecimal sum,
        int count
) {
}
