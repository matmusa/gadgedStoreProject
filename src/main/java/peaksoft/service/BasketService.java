package peaksoft.service;

import peaksoft.dto.response.AllProductInformationResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface BasketService {


    SimpleResponse addProductToUserBasket(Long productId);

    SimpleResponse deleteProductFromUserBasKet(Long productId);

    public List<ProductResponse> getAllInformationUserProduct();


    List<AllProductInformationResponse> allProductInformationResponse();
}
