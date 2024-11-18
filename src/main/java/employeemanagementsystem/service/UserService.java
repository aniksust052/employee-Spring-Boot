package employeemanagementsystem.service;

import employeemanagementsystem.dto.LoginRequest;
import employeemanagementsystem.dto.ResponseDto;
import employeemanagementsystem.dto.SignUpRequest;
import employeemanagementsystem.model.Role;
import employeemanagementsystem.model.User;
import employeemanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseDto login(LoginRequest loginRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
//            Authentication authRes =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            responseDto.setStatusCode(200);
            responseDto.setToken(jwt);
            responseDto.setRole(user.getRole());
            responseDto.setRefreshToken(refreshToken);
            responseDto.setExpirationTime("24hrs");
            responseDto.setMessage("Successfully logged in");
//            SecurityContextHolder.getContext().setAuthentication(authRes);
        } catch (AuthenticationException e) {
            responseDto.setStatusCode(401);
            responseDto.setMessage("Invalid email or password");
        }
        return responseDto;
    }


    public ResponseDto refreshToken(ResponseDto refreshTokenRequest) {
        ResponseDto responseDto = new ResponseDto();

        try {
            String email = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            User user = userRepository.findByEmail(email).orElseThrow();

            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
                var jwt = jwtUtils.generateToken(user);
                responseDto.setStatusCode(200);
                responseDto.setToken(jwt);
                responseDto.setRefreshToken(refreshTokenRequest.getToken());
                responseDto.setExpirationTime("24hrs");
                responseDto.setMessage("Successfully Refreshed Token");
            }
            responseDto.setStatusCode(200);
            return responseDto;
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setMessage("Error Refreshing Token");
            return responseDto;
        }
    }


    public ResponseDto getAllUsers() {
        ResponseDto responseDto = new ResponseDto();

        try {
            List<User> users = userRepository.findAll();
            if (!users.isEmpty()) {
                responseDto.setStatusCode(200);
                responseDto.setUserList(users);
                responseDto.setMessage("Successfully Fetched Users");
            } else {
                responseDto.setStatusCode(404);
                responseDto.setMessage("No users found");
            }
            return responseDto;
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setMessage("Error Fetching Users");
            return responseDto;
        }
    }

    public ResponseDto getUserById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            responseDto.setStatusCode(200);
            responseDto.setUser(user);
            responseDto.setMessage("Successfully Fetched User with id : " + id);
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setMessage("Error Fetching User with id : " + id);
        }
        return responseDto;
    }


    public User registerUser(SignUpRequest signUpRequest) {
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(signUpRequest.getRole())
                .build();
        return userRepository.save(user);
    }

}
