package peaksoft.service;


import peaksoft.dto.response.FavoriteResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface FavoriteService {

    SimpleResponse addProductToFavorite(Long userId, Long productId);

    SimpleResponse deleteProductFromFavorite(Long userId, Long productId);

    List<ProductResponse> getAllProductFromFavoriteFromUser(Long userId);

}
