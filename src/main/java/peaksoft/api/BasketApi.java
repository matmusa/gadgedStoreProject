package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.response.BasketResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.BasketService;

import java.util.List;

@RestController
@RequestMapping("/baskets")
@RequiredArgsConstructor
public class BasketApi {

    private final BasketService basketService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}/{productId}")
    public SimpleResponse addProductToUserBasket(@PathVariable Long userId, @PathVariable Long productId) {
        return basketService.addProductToUserBasket(userId, productId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}/{productId}")
    public SimpleResponse deleteProductFromUserBasket(@PathVariable Long userId, @PathVariable Long productId) {
        return basketService.deleteProductFromUserBasKet(userId, productId);
    }
@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public List<ProductResponse> getAllProductFromUserBasket(@PathVariable Long userId) {
        return basketService.getAllInformationUserProduct(userId);

    }
}
