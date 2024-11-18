//package employeemanagementsystem.service;
//
//
//import employeemanagementsystem.model.Role;
//import employeemanagementsystem.model.User;
//import employeemanagementsystem.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        System.out.println("This is custom authentication in service layer");
//        String email = authentication.getName();
//        String password = (String) authentication.getCredentials();
//        Optional<User> user = userRepository.findByEmail(email);
//
//
//        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
//            if (user.get().getRole().equals(Role.MANAGER) && user.get().getEmail().equals(email)) {
//                return new UsernamePasswordAuthenticationToken(user.get(), password, user.get().getAuthorities());
//            }
//        }
//        throw new BadCredentialsException("Invalid credentials");
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        System.out.println("This custom support in service layer");
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
