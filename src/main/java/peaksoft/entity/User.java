package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peaksoft.enums.Role;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User  implements UserDetails {
    @Id
    @GeneratedValue(
            generator = "user_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "user_gen",
            sequenceName = "users_seq",
            allocationSize = 1
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ZonedDateTime createdDate;
    private ZonedDateTime updateDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST,
                    CascadeType.REMOVE})
    private List<Favorite> favorites;
    @OneToOne(mappedBy = "user",
            cascade = {CascadeType.ALL})
    private Basket basket;
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST,
                    CascadeType.REMOVE})
    List<Comment> comments;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return email;
    }

    @Override
    public String getUsername() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(Long id, String firstName, String lastName, String email, String password, ZonedDateTime createdDate, ZonedDateTime updateDate, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.role = role;
    }
}
