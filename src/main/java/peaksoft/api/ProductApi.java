package peaksoft.api;


import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.ProductGetAllInformation;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.enums.Category;
import peaksoft.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductService productService;

    @PermitAll
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{brandId}")
    public SimpleResponse saveProduct(@PathVariable Long brandId,@RequestBody ProductRequest productRequest) {
        return productService.saveProduct(brandId,productRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse updateProductById(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteProductById(@PathVariable Long id) {
        return productService.deleteProductById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/all/{id}")
    public ProductGetAllInformation getAllInformation(@PathVariable Long id) {
        return productService.getAllInformationFromProduct(id);
    }

   @PermitAll
    @GetMapping("/filter")
    public List<ProductResponse> getProductsByFilter(@RequestParam String category, @RequestParam String ascOrDesc) {
       Category productCategory = Category.valueOf(category);
       return productService.getProductsByFilter(productCategory, ascOrDesc);
    }


}
