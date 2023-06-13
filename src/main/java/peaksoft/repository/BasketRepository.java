package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.ProductResponse;
import peaksoft.entity.Basket;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    @Query("select new peaksoft.dto.response.ProductResponse(" +
            "p.id,p.name,p.price,p.images,p.characteristic,p.category)" +
            " from Basket b join b.products p where b.user.id=:userId")
    List<ProductResponse> getAllProductsUserId(@Param("userId") Long userId);

    @Query("select count (p.id) from Product  p join p.baskets pb  join pb.user pbu  where pbu.id=:userId")
    int count(Long userId);

//    @Query("SELECT SUM(p.price) FROM Product p, Basket b, User u " +
//            "WHERE p.basket = b " +
//            "AND b.user = u " +
//            "AND u.id = :userId")
//    BigDecimal sum(@Param("userId") Long userId);


//        @Query("SELECT SUM(p.price) FROM Product p " +
//            "JOIN p.baskets pb " +
//            "JOIN pb.user u " +
//            "WHERE u.id = :userId")
//        BigDecimal sum(@Param("userId") Long userId);

//    @Query("SELECT SUM(p.price) FROM Product p, Baskets b, Users u " +
//            "WHERE p.id = b.product.id " +
//            "AND b.user.id = u.id " +
//            "AND u.id = :userId")
//    int sum(@Param("userId") Long userId);

}
