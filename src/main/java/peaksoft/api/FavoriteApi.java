package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}/{productId}")
    public SimpleResponse addProductToFavorite(@PathVariable Long userId, @PathVariable Long productId) {
        return favoriteService.addProductToFavorite(userId, productId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}/{productId}")
    private SimpleResponse deleteProductFromFavorite(@PathVariable Long userId, @PathVariable Long productId) {
        return favoriteService.deleteProductFromFavorite(userId, productId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public List<ProductResponse> getAllProductFromFavoriteFromUser(@PathVariable Long userId) {
        return favoriteService.getAllProductFromFavoriteFromUser(userId);
    }
}
