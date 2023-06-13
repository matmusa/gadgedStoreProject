package peaksoft.service;

import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.ProductGetAllInformation;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.enums.Category;

import java.util.List;

public interface ProductService {


    SimpleResponse saveProduct(Long brandId,ProductRequest productRequest);

    ProductResponse getProductById(Long id);

    SimpleResponse updateProduct(Long id, ProductRequest productRequest);

    SimpleResponse deleteProductById(Long id);

    ProductGetAllInformation getAllInformationFromProduct(Long id);

    List<ProductResponse>getProductsByFilter(Category category, String sortOrder);

}
