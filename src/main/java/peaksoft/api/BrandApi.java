package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.BrandRequest;
import peaksoft.dto.response.BrandResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Brand;
import peaksoft.service.BrandService;

import java.util.List;

@RestController
@RequestMapping("brands")
@RequiredArgsConstructor
public class BrandApi {

    private final BrandService brandService;

    @PermitAll
    @GetMapping
    public List<BrandResponse> getAllBrands() {
        return brandService.getAllBrands();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveBrand(@RequestBody BrandRequest request) {
        return brandService.saveBrand(request);
    }

    @PermitAll
    @GetMapping("/{id}")
    public BrandResponse getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping({"/{id}"})
    SimpleResponse updateBrandById(@PathVariable Long id, @RequestBody BrandRequest request) {
        return brandService.updateBrandById(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping({"/{id}"})
    SimpleResponse deleteBrandById(@PathVariable Long id) {
        return brandService.deleteBrandById(id);
    }


}
