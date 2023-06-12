package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(
            generator = "brand_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "brand_gen",
            sequenceName = "brand_seq",
            allocationSize = 1
    )
    private Long id;
    private String brandName;
    private String image;

    @OneToMany(
            mappedBy = "brand",
            cascade = CascadeType.ALL)
    List<Product> products;


}
