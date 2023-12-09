package Controller;

// src/main/java/com/example/votingapp/controller/UserController.java
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Check if the username or email already exists
        if (userRepository.findByUsername(user.getUsername()) != null ||
                userRepository.findByUsername(user.getName()) != null) {
            return ResponseEntity.badRequest().body("Username or email already exists");
        }

        // Encrypt the password before saving to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        // Retrieve the user by username
        Optional<Object> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser == null || !passwordEncoder.matches(user.getPassword(), String.valueOf(existingUser.getClass()))) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        return ResponseEntity.ok("Login successful");
    }

    // Additional methods for profile management, etc.
}

