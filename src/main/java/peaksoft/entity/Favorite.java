package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;

import java.util.List;

@Entity
@Table(name = "favorites")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Favorite {
    @Id
    @GeneratedValue(
            generator = "favorite_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "favorite_gen",
            sequenceName = "favorite_seq",
            allocationSize = 1
    )
    private Long id;
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.MERGE})
    private User user;
    @OneToMany(mappedBy = "favorite",
            cascade = {CascadeType.DETACH,
                   //  CascadeType.MERGE,
                    CascadeType.REFRESH)
    private List<Product> product;


}
