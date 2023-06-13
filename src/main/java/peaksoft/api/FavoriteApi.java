package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteApi {
    private final FavoriteService favoriteService;

    @PermitAll
    @PostMapping("/{productId}")
    public SimpleResponse addProductToFavorite(@PathVariable Long productId) {
        return favoriteService.addProductToFavorite(productId);
    }

    @PermitAll
    @DeleteMapping("/{productId}")
    private SimpleResponse deleteProductFromFavorite(@PathVariable Long productId) {
        return favoriteService.deleteProductFromFavorite(productId);
    }

    @PermitAll
    @GetMapping()
    public List<ProductResponse> getAllProductFromFavoriteFromUser() {
        return favoriteService.getAllProductFromFavoriteFromUser();
    }
}
