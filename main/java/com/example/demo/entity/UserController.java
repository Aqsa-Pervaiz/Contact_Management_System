package com.example.demo.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    public UserRepository userRepository;
    
@PostMapping("/register")
public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
    Map<String, String> response = new HashMap<>();
    String email = (user.getEmail() != null) ? user.getEmail().trim() : null;
    String phone = (user.getPhone() != null) ? user.getPhone().trim() : null;

    if ((email == null || email.isEmpty()) && (phone == null || phone.isEmpty())) {
        response.put("message", "Either email or phone number is required!");
        return ResponseEntity.badRequest().body(response);
    }
    if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
        response.put("message", "Password cannot be empty!");
        return ResponseEntity.badRequest().body(response);
    }
    if (email != null && !email.isEmpty()) {
        if (userRepository.findByEmail(email) != null) {
            response.put("message", "Email already registered!");
            return ResponseEntity.badRequest().body(response);
        }
        user.setEmail(email);
    }

    if (phone != null && !phone.isEmpty()) {
        if (userRepository.findByPhone(phone) != null) {
            response.put("message", "Phone number already registered!");
            return ResponseEntity.badRequest().body(response);
        }
        user.setPhone(phone);
    }
    userRepository.save(user);
    response.put("message", "User Registered Successfully!");
    return ResponseEntity.ok(response);
}

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
    Map<String, String> response = new HashMap<>();
    User existingUser = null;
    if (user.getEmail() != null) {
        existingUser = userRepository.findByEmail(user.getEmail());
    } 
    else if (user.getPhone() != null) {
        existingUser = userRepository.findByPhone(user.getPhone());
    }
    if (existingUser != null && user.getPassword().equals(existingUser.getPassword())) {
        response.put("message", "Login Successful!");
        return ResponseEntity.ok(response);
    }
     else {
        response.put("message", "Invalid email/phone or password!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return "User deleted successfully!";
    }
    
}
