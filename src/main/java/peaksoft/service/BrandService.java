package peaksoft.service;

import peaksoft.dto.request.BasketRequest;
import peaksoft.dto.request.BrandRequest;
import peaksoft.dto.response.BasketResponse;
import peaksoft.dto.response.BrandResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAllBrands();

    SimpleResponse saveBrand(BrandRequest brandRequest);

    BrandResponse getBrandById(Long id);

    SimpleResponse updateBrandById(Long id,BrandRequest brandRequest);

    SimpleResponse deleteBrandById(Long id);



























}
