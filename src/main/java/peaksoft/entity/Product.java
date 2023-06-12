package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.enums.Category;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(
            generator = "product_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "product_gen",
            sequenceName = "product_seq",
            allocationSize = 1
    )
    private Long id;
    private String name;
    private String price;
    private List<String> images;
    private String characteristic;
    private Boolean isFavorite;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<Basket> baskets;
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private Favorite favorite;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private Comment comment;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private Brand brand;
}
