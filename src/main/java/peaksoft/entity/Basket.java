package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "baskets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(
            generator = "basket_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "basket_gen",
            sequenceName = "basket_seq",
            allocationSize = 1
    )
    private Long id;
    @OneToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH})
    private User user;
    @ManyToMany(cascade = {CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH})
    private List<Product> products;


}
