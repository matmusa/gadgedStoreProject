package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.config.JWTService;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Favorite;
import peaksoft.entity.Product;
import peaksoft.entity.User;
import peaksoft.repository.FavoriteRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final JWTService jwtService;

    @Override
    public SimpleResponse addProductToFavorite(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with id %s doesn't exist ", userId)));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Product with  id %s doesnt exist ", productId)));
        User user1=jwtService.
        List<Product> products = new ArrayList<>();
        List<Favorite> favorites = new ArrayList<>();
        products.add(product);
        Favorite favorite = new Favorite();
        favorite.setProduct(products);
        product.setFavorite(favorite);
        user.setFavorites(favorites);
        favorite.setUser(user);
        userRepository.save(user);
        productRepository.save(product);
        favoriteRepository.save(favorite);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Product with name %s successfully added to user with name %s to  favorite table ", product.getName(), user.getFirstName()))
                .build();
    }

    @Override
    public SimpleResponse deleteProductFromFavorite(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with id %s doesn't exist ", userId)));


        List<Favorite> favorites = user.getFavorites();
        for (Favorite f : favorites
        ) {
            List<Product> products = f.getProduct();
            boolean removed = products.removeIf(product -> product.getId().equals(productId));

                if (removed) {
                    f.setProduct(products);
                    favoriteRepository.save(f);

                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message(String.format(""))
                        .build();

            }
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(String.format("Product with name %s not found ", productId))
                .build();

    }

    @Override
    public List<ProductResponse> getAllProductFromFavoriteFromUser(Long userId) {
        return favoriteRepository.getAllProducts(userId);
    }
}
