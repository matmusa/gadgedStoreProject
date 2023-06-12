package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.getAllUsers();
    }

    @Override
    public SimpleResponse saveUsers(UserRequest userRequest) {
        User user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .password(userRequest.password())
                .createdDate(ZonedDateTime.now())
                .updateDate(ZonedDateTime.now())
                .build();
        userRepository.save(user);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with id %s successfully saved! ", user.getId()))
                .build();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        return userRepository.getUserById(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException
                                (String.format("User with id %s doesn't exist !", userId)));

    }

    @Override
    public SimpleResponse updateUserById(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException
                        (String.format("User with id %s doesn't exist !", userId)));
        user.setLastName(userRequest.lastName());
        user.setFirstName(userRequest.firstName());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        userRepository.save(user);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with if %s successfully updated !",user.getId()))
                .build();
    }

    @Override
    public SimpleResponse deleteUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException
                        (String.format("User with id %s doesn't exist !", userId)));
        userRepository.delete(user);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format(
                         "User with if %s successfully deleted !",user.getId()))
                .build();
    }
}
