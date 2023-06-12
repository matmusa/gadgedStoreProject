package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.service.UserService;


import java.util.List;

@Service
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @PermitAll
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public SimpleResponse saveUser(@RequestBody UserRequest userRequest) {
        return userService.saveUsers(userRequest);
    }


    @PermitAll
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse updateUserById(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUserById(id, userRequest);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    private SimpleResponse deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
