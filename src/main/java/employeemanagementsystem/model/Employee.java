package employeemanagementsystem.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long registrationNumber;

    @Email(message = "Email should be valid")
    private String ownerEmail;

    @Column(nullable = false)
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    private String salery;

    @Column(nullable = false)
    private String department;
    private String address;
}
