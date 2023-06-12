package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.BasketResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.entity.Basket;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    @Query("select new peaksoft.dto.response.ProductResponse(" +
            "p.name,p.images,p.isFavorite,p.price)" +
            " from Product p join p.baskets pb join pb.user pbu where pbu.id=:userId")
            List<ProductResponse> getAllProductsFromBasket(@Param("userId") Long userId);


}
