package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.response.AllProductInformationResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.BasketService;

import java.util.List;

@RestController
@RequestMapping("/baskets")
@RequiredArgsConstructor
public class BasketApi {

    private final BasketService basketService;

    @PermitAll
    @PostMapping("/{productId}")
    public SimpleResponse addProductToUserBasket(@PathVariable Long productId) {
        return basketService.addProductToUserBasket(productId);
    }

    @PermitAll
    @DeleteMapping("/{productId}")
    public SimpleResponse deleteProductFromUserBasket(@PathVariable Long productId) {
        return basketService.deleteProductFromUserBasKet(productId);
    }

    @PermitAll
    @GetMapping
    public List<ProductResponse> getAllProductFromUserBasket() {
        return basketService.getAllInformationUserProduct();
    }
    @PermitAll
    @GetMapping("/all")
    public List<AllProductInformationResponse>getAllInformationFromUserBasket(){
        return basketService.allProductInformationResponse();
    }

}
