package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    public SimpleResponse addProductToUserBasket(Long userId, Long productId) {
        Basket basket = new Basket();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with  id %s doesn't exist !", userId)));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Product  with id %s  doesn't exist !", productId)));
        List<Product> products = new ArrayList<>();
        products.add(product);
        basket.setUser(user);
        user.setBasket(basket);
        basket.setProducts(products);
        basketRepository.save(basket);
        userRepository.save(user);
        productRepository.save(product);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Product  with id %s successfully added User with if %s id basket ! ", product.getId(), user.getId()))
                .build();
    }

    @Override
    public SimpleResponse deleteProductFromUserBasKet(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with  id %s doesn't exist !", userId)));
        Basket basket = user.getBasket();
        List<Product> products = basket.getProducts();
        boolean removed=products.removeIf(product -> product.getId().equals(productId));
        if(removed){
            basket.setProducts(products);
            basketRepository.save(basket);

            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(String.format("Product with %s id successfully removed from user with id %s basket",productId,userId))
                    .build();
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(String.format("Product with id %s not found !",productId))
                .build();

    }

    @Override
    public List<ProductResponse> getAllInformationUserProduct(Long userId) {
        return basketRepository.getAllProductsFromBasket(userId);
    }
}
