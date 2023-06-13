package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.config.JwtService;
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
    private final JwtService jwtService;

    @Override
    public SimpleResponse addProductToFavorite(Long productId) {
       User user=jwtService.getAuthentication();
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Product with  id %s doesnt exist ", productId)));
        List<Product>products = new ArrayList<>();
        List<Favorite> favorites = new ArrayList<>();
        products.add(product);
        Favorite favorite = new Favorite();
        favorite.setProduct(products);
        product.setFavorite(favorite);
        user.setFavorites(favorites);
        favorite.setUser(user);
        favoriteRepository.save(favorite);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Product with name %s successfully added to user with name %s to  favorite table ", product.getName(), user.getFirstName()))
                .build();
    }


    @Override
    public SimpleResponse deleteProductFromFavorite( Long productId) {
        User user=jwtService.getAuthentication();
        List<Favorite> favorites = user.getFavorites();
        for (Favorite f : favorites
        ) {
            List<Product> products = f.getProduct();
            boolean removed = products.removeIf(product -> product.getId().equals(productId));

                if (removed) {
                    f.setProduct(products);
                    favoriteRepository.delete(f);
                    favoriteRepository.save(f);

                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message(String.format("Product with id %s successfully removed !",productId))
                        .build();

            }
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(String.format("Product with name %s not found ", productId))
                .build();

    }



    @Override
    public List<ProductResponse> getAllProductFromFavoriteFromUser() {
        User user=jwtService.getAuthentication();
        return favoriteRepository.getAllProducts(user.getId());
    }
}
