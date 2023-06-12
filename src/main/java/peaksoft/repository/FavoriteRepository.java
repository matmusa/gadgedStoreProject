package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import peaksoft.dto.response.FavoriteResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.entity.Favorite;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query("select new peaksoft.dto.response.FavoriteResponse(f.id,fu,fp) from Favorite  f  join f.user fu join f.product fp")
    List<FavoriteResponse> getAllByFavoriteByUsersId(@Param("usersId") Long id);



    @Query("select new peaksoft.dto.response.ProductResponse(" +
            "p.name,p.images,p.isFavorite,p.price) from Product" +
            "  p join p.baskets pb join pb.user pbu join pbu.favorites pbuf " +
            "    where pbu.id=:userId and p.favorite.id=pbuf.id")
    List<ProductResponse> getAllProducts(@Param("userId") Long userId);
}
