package employeemanagementsystem.dto;

import employeemanagementsystem.model.Role;
import lombok.Data;

@Data
public class SignUpRequest {

    private String email;
    private String password;
    private Role role;
}
