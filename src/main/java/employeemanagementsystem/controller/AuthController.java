package employeemanagementsystem.controller;

import employeemanagementsystem.dto.ResponseDto;
import employeemanagementsystem.service.UserService;
import employeemanagementsystem.dto.LoginRequest;
import employeemanagementsystem.dto.SignUpRequest;
import employeemanagementsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login (@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }


    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody SignUpRequest request) {
        Optional<User> admin = Optional.ofNullable(userService.registerUser(request));
        if (admin.isPresent() && admin.get().getId() > 0) {
            return ResponseEntity.ok("Admin registration successful");
        }
        return ResponseEntity.ok("Admin registration unsuccessful");
    }


    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("No user is currently logged in.");
        }

        User loggedInUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of(
                "email", loggedInUser.getEmail(),
                "role", loggedInUser.getRole()
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto> refresh (@RequestBody ResponseDto responseDto) {
        return ResponseEntity.ok(userService.refreshToken(responseDto));
    }

}
