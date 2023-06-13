package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.config.JwtService;
import peaksoft.dto.response.AllProductInformationResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Basket;
import peaksoft.entity.Product;
import peaksoft.entity.User;
import peaksoft.repository.BasketRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.BasketService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final JwtService jwtService;

    @Override
    public SimpleResponse addProductToUserBasket(Long productId) {
        User user = jwtService.getAuthentication();
        Basket basket = user.getBasket();

        if (basket == null) {
            basket = new Basket();
            basket.setUser(user);
            basket.setProducts(new ArrayList<>());
        }

        Product product = productRepository.findById(productId).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Product with id %s doesn't exist!", productId)));

        List<Product> products = basket.getProducts();
        products.add(product);

        basketRepository.save(basket);
        userRepository.save(user);
        productRepository.save(product);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Product with id %s successfully added to User with id %s basket!", product.getId(), user.getId()))
                .build();
    }


    @Override
    public SimpleResponse deleteProductFromUserBasKet(Long productId) {

        User user1 = jwtService.getAuthentication();
        Basket basket = user1.getBasket();
        List<Product> products = basket.getProducts();
        boolean removed = products.removeIf(product -> product.getId().equals(productId));
        if (removed) {
            basket.setProducts(products);
            basketRepository.save(basket);

            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(String.format("Product with %s id successfully removed from user with id %s basket", productId, user1.getId()))
                    .build();
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(String.format("Product with id %s not found !", productId))
                .build();

    }

    @Override
    public List<ProductResponse> getAllInformationUserProduct() {
        User user = jwtService.getAuthentication();
        return basketRepository.getAllProductsUserId(user.getId());

    }


    @Override
    public List<AllProductInformationResponse> allProductInformationResponse() {
        User user = jwtService.getAuthentication();

        AllProductInformationResponse allProductInformationResponse = AllProductInformationResponse.builder()
                .productResponseList(basketRepository.getAllProductsUserId(user.getId()))
                //.sum(basketRepository.sum(user.getId()))
                .count(basketRepository.count(user.getId()))
                .build();
        List<AllProductInformationResponse> allProductInformationResponses = new ArrayList<>();
        allProductInformationResponses.add(allProductInformationResponse);
        return allProductInformationResponses;
    }
}




