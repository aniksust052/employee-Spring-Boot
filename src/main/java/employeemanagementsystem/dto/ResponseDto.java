package employeemanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import employeemanagementsystem.model.Role;
import employeemanagementsystem.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto {
    private int statusCode;
    private String error;
    private String message;
    private String errorMessage;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String email;
    private Role role ;
    private String password;
    private User user;
    private List<User> userList;
}
