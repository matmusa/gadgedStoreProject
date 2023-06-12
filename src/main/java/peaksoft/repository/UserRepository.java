package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query("select new peaksoft.dto.response.UserResponse(u.id,u.firstName,u.firstName,u.email,u.password,u.role) from User u")
    List<UserResponse> getAllUsers();

    Optional<User> getUserByEmail(String email);

    Optional<UserResponse> getUserById(Long userId);

    boolean existsByEmail(String email);


}
