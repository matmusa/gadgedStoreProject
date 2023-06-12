package peaksoft.service;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.ProductGetAllInformation;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface ProductService {


    SimpleResponse saveProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long id);

    SimpleResponse updateProduct(Long id, ProductRequest productRequest);

    SimpleResponse deleteProductById(Long id);

    ProductGetAllInformation getAllInformationFromProduct(Long id);

    List<ProductResponse>getProductsByFilter(String category,String ascOrDesc);

}
