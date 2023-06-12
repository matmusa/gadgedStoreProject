package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import peaksoft.dto.response.ProductResponse;
import peaksoft.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<ProductResponse>getProductById(Long id);

    @Query("select pc.comment from Product  p join p.comment pc where p.id=:id")
    List<String> getAllCommentsFromProduct(@Param("id") Long id);
    @Query("select count (pf.id) from Product p join p.favorite pf where p.id=:id ")
    int getAllFavorites(@Param("id") Long id);

    @Query("select new peaksoft.dto.response.ProductResponse(" +
            "p.name,p.images,p.isFavorite,p.price) from Product" +
            " p where p.category=:category order by case when:sortOrder='asc' then p.price end  asc ," +
            "case when:sortOrder='desc' then p.price end desc ")
    List<ProductResponse> getAllProductsByFilter(@Param("category") String category, @Param("sortOrder")String sortOrder);



}
