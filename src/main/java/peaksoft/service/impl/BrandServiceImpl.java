package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.BrandRequest;
import peaksoft.dto.response.BrandResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Brand;
import peaksoft.repository.BrandRepository;
import peaksoft.service.BrandService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<BrandResponse> getAllBrands() {
        return brandRepository.getAllBrands();
    }

    @Override
    public SimpleResponse saveBrand(BrandRequest brandRequest) {
        Brand brand = new Brand();
        brand.setBrandName(brandRequest.brandName());
        brand.setImage(brandRequest.image());
        brandRepository.save(brand);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Brand with name %s successfully saved!"))
                .build();
    }

    @Override
    public BrandResponse getBrandById(Long id) {
        return brandRepository.getBrandById(id)
                .orElseThrow(()->new NullPointerException(String.format("Brand with doesn't exist",id)));
    }

    @Override
    public SimpleResponse updateBrandById(Long id, BrandRequest brandRequest) {
        Brand brand=brandRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException(String.format("User with id doesn't exist !",id)));
        brand.setBrandName(brandRequest.brandName());
        brand.setImage(brandRequest.image());
        brandRepository.save(brand);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Brand with id  %s successfully updated !",id))
                .build();
    }

    @Override
    public SimpleResponse deleteBrandById(Long id) {
        Brand brand=brandRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException(String.format("User with id doesn't exist !",id)));
        brandRepository.delete(brand);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Brand with id  %s successfully deleted !",id))
                .build();
    }
}
