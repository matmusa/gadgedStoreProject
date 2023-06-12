package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(
            generator = "comment_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "comment_gen",
            sequenceName = "comment_seq",
            allocationSize = 1
    )
    private Long id;
    private String comment;
    private ZonedDateTime createdDate;

    @ManyToOne(
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH})
    private User user;
    @OneToMany(
            mappedBy = "comment",
            cascade ={CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH}
    )
    private List<Product> products;
}
