package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.config.JWTService;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.AlreadyExistException;
import peaksoft.repository.UserRepository;
import peaksoft.service.AuthenticationService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new AlreadyExistException( )
        User user = User.builder()
                .firstName(signUpRequest.firstName())
                .lastName(signUpRequest.lastName())
                .email(signUpRequest.email())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .role(signUpRequest.role())
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
        User user = userRepository.getUserByEmail(signInRequest.email()).orElseThrow(() -> new UsernameNotFoundException("user is not found"));
        if (signInRequest.email().isBlank()){
            throw new BadCredentialsException("email does not exist...");
        }
        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .role(user.getRole())
                .email(user.getEmail())
                .build();


    }


    @PostConstruct
    private void initializeAdmin() {
        User admin = new User();
        admin.setEmail("m@gmail.com");
        admin.setPassword(passwordEncoder.encode("matmusa123"));
        admin.setRole(Role.ADMIN);
        admin.setLastName("tashtanov");
        admin.setFirstName("matmusa");
        if (!userRepository.existsByEmail("m@gmail.com")) {
            userRepository.save(admin);
        }
    }

    /*import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JWTService;
import peaksoft.dto.dtoAuthentication.AdminTokenRequest;
import peaksoft.dto.dtoAuthentication.AuthenticationRequest;
import peaksoft.dto.dtoAuthentication.AuthenticationResponse;
import peaksoft.dto.dtoAuthentication.SignIn;
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
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse adminToken(AdminTokenRequest adminTokenRequest) {
        // authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.email(),authenticationRequest.password()));
//        User user = userRepository.getUserByEmail(adminTokenRequest.email()).orElseThrow(() -> new UsernameNotFoundException("user with email: " +adminTokenRequest.email()  + " is not found!"));
        User user1 = userRepository.findById(1L).orElseThrow(() -> new UsernameNotFoundException("user with email: 1 is not found!"));
        String token = jwtService.generateToken(user1);
        System.out.println(token);
        return AuthenticationResponse.builder()
                .email(user1.getEmail())
                .token(token)
                .role(user1.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse signUp(AuthenticationRequest authenticationRequest) {
        if (userRepository.existsByEmail(authenticationRequest.email())){
            throw new  EntityExistsException("user with email: "+ authenticationRequest.email()+" already exist");
        }
        User user = new User();
        user.setFirstName(authenticationRequest.firstName());
        user.setLastName(authenticationRequest.lastName());
        user.setEmail(authenticationRequest.email());
        user.setRole(authenticationRequest.role());
        user.setPassword(passwordEncoder.encode(authenticationRequest.password()));
        user.setCreatedAt(ZonedDateTime.now());
        user.setUpdateDate(ZonedDateTime.now());
        userRepository.save(user);
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignIn signIn) {
        User user = userRepository.getUserByEmail(signIn.email()).orElseThrow(() -> new UsernameNotFoundException("user is not found"));
        if (signIn.email().isBlank()){
            throw new BadCredentialsException("email does not exist...");
        }
        if (!passwordEncoder.matches(signIn.password(), user.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }
    **/
}
