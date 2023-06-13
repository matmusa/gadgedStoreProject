package peaksoft.service;


import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface FavoriteService {

    SimpleResponse addProductToFavorite(Long productId);

    SimpleResponse deleteProductFromFavorite( Long productId);

    List<ProductResponse> getAllProductFromFavoriteFromUser( );

}
