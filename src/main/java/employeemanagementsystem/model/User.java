package employeemanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Transient
    private String rawPassword;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the roles of the user as authorities
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getUsername() {
        return email; // Assuming email is used as the username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Account is always non-expired in this case
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Account is always non-locked in this case
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credentials are always non-expired in this case
    }

    @Override
    public boolean isEnabled() {
        return true; // User is always enabled in this case
    }
}
