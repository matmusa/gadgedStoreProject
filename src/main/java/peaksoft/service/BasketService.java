package peaksoft.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import peaksoft.dto.request.BasketRequest;
import peaksoft.dto.response.BasketResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;

import java.beans.SimpleBeanInfo;
import java.util.List;

public interface BasketService {


    SimpleResponse addProductToUserBasket(Long userId, Long productId);

    SimpleResponse deleteProductFromUserBasKet(Long userId, Long productId);

    @Query("select count (p.id),sum (p.price) from Product p join p.baskets pb join pb.user pbu where pbu.id=:userId")
    List<ProductResponse> getAllInformationUserProduct(@Param("userId") Long userId);


}
