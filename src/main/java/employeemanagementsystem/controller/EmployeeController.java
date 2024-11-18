package employeemanagementsystem.controller;

import employeemanagementsystem.model.Employee;
import employeemanagementsystem.service.EmployeeService;
import employeemanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = {"*", "http://localhost:5173"})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Employee> getAllEmployee(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return employeeService.getAllEmployee(username);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee, @AuthenticationPrincipal UserDetails userDetails) {
        Random rand = new Random();
        long randomNumber = 1000000000L + (long) (rand.nextDouble() * 9000000000L);

//        SignUpRequest signUpRequest = new SignUpRequest();
//        signUpRequest.setEmail(employee.getEmail());
//        signUpRequest.setRole(Role.valueOf("EMPLOYEE"));
//        signUpRequest.setPassword(String.valueOf(randomNumber));
//        userService.registerUser(signUpRequest);

        employee.setRegistrationNumber(randomNumber);
        employee.setOwnerEmail(userDetails.getUsername());
        return employeeService.saveEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    employee.setFirstName(employeeDetails.getFirstName());
                    employee.setLastName(employeeDetails.getLastName());
                    employee.setEmail(employeeDetails.getEmail());
                    employee.setSalery(employeeDetails.getSalery());
                    employee.setDepartment(employeeDetails.getDepartment());
                    employee.setAddress(employeeDetails.getAddress());
                    return ResponseEntity.ok(employeeService.saveEmployee(employee));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    employeeService.deleteEmployee(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
