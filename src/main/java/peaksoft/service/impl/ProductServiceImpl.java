package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.ProductGetAllInformation;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Product;
import peaksoft.repository.ProductRepository;
import peaksoft.service.ProductService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    @Override
    public SimpleResponse saveProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.name());
        product.setImages(productRequest.images());
        product.setCategory(productRequest.category());
        product.setPrice(productRequest.price());
        product.setCharacteristic(productRequest.characteristic());
        product.setIsFavorite(false);
        productRepository.save(product);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Product with name %s successfully saved !", product.getName()))
                .build();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.getProductById(id).
                orElseThrow(() ->
                        new NullPointerException(String.format("Product with id %s doesn't exist !", id)));

    }

    @Override
    public SimpleResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NullPointerException(String.format("Product with  id %s doesn't exist !", id)));
        product.setName(productRequest.name());
        product.setImages(productRequest.images());
        product.setCategory(productRequest.category());
        product.setPrice(productRequest.price());
        product.setCharacteristic(productRequest.characteristic());
        product.setIsFavorite(false);
        productRepository.save(product);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Product with name %s successfully updated!", product.getName()))
                .build();

    }

    @Override
    public SimpleResponse deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NullPointerException(String.format("Product with  id %s doesn't exist !", id)));
        productRepository.delete(product);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Product with name %s successfully deleted!", product.getName()))
                .build();
    }

    @Override
    public ProductGetAllInformation getAllInformationFromProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NullPointerException(String.format("Product with  id %s doesn't exist !", id)));
        return ProductGetAllInformation.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .price(product.getPrice())
                .characteristic(product.getCharacteristic())
                .images(product.getImages())
                .isFavorite(product.getIsFavorite())
                .comments(productRepository.getAllCommentsFromProduct(id))
                .favorites(productRepository.getAllFavorites(id))
                .build();
    }

    @Override
    public List<ProductResponse> getProductsByFilter(String category, String ascOrDesc) {
        return productRepository.getAllProductsByFilter(category, ascOrDesc);
    }


}
