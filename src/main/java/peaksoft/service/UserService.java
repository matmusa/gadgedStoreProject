package peaksoft.service;

import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    SimpleResponse saveUsers(UserRequest userRequest);

    UserResponse getUserById(Long userId);

    SimpleResponse updateUserById(Long userId, UserRequest userRequest);

    SimpleResponse deleteUserById(Long userId);


}
