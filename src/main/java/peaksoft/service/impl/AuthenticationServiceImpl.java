package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.repository.UserRepository;
import peaksoft.service.AuthenticationService;

import java.time.ZonedDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
   private final PasswordEncoder passwordEncoder;


    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new EntityExistsException(String.format(
                    "User with email: %s already exists!", signUpRequest.email()));
        }

        User user = User.builder()
                .firstName(signUpRequest.firstName())
                .lastName(signUpRequest.lastName())
                .email(signUpRequest.email())
                .password(signUpRequest.password())
                .role(Role.USER)
                .build();
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        if (signInRequest.email().isBlank()) {
            throw new BadCredentialsException("Email doesn't exist!");
        }

        User user = userRepository.getUserByEmail(signInRequest.email())
                .orElseThrow(() -> new EntityNotFoundException(
                        "USer with email: " + signInRequest.email() + " not found"
                ));

        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
             throw new BadCredentialsException("Incorrect password!");
        }



        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }

    @PostConstruct
    private void initializeAdmin() {
        User admin = new User();
        admin.setEmail("p@gmail.com");
        admin.setCreatedDate(ZonedDateTime.now());
        admin.setPassword(passwordEncoder.encode("p222"));
        admin.setRole(Role.ADMIN);
        admin.setLastName("tashtanov");
        admin.setFirstName("matmusa");

        if (!userRepository.existsByEmail("p@gmail.com")) {
            userRepository.save(admin);
        }


    }


}
