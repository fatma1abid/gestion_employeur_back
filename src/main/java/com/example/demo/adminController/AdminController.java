package com.example.demo.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.adminModel.AdminModel; // Correct import
import com.example.demo.adminRepository.adminRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AdminController { // Renamed to AdminController

	@Autowired
	private adminRepository repo;

	// Method for admin login
	// Method for logging in an admin
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody AdminModel adminRequest) {
		// Validate the login credentials
		AdminModel admin = repo.findByAdminName(adminRequest.getAdminName());

		if (admin == null || !admin.getAdminPassword().equals(adminRequest.getAdminPassword())) {
			// Return an error message if credentials are invalid
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("message", "Invalid credentials");
			return ResponseEntity.status(401).body(errorResponse);  // Unauthorized error
		}

		// If login is successful, return a success message or token
		Map<String, String> successResponse = new HashMap<>();
		successResponse.put("message", "Login successful");
		return ResponseEntity.ok(successResponse);  // 200 OK response
	}


	// Method for registering a new admin
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@RequestBody AdminModel adminRequest) {
		AdminModel existingAdmin = repo.findByAdminName(adminRequest.getAdminName());

		if (existingAdmin != null) {
			// Return a JSON response with the error message
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("message", "Admin name already exists!");
			return ResponseEntity.status(400).body(errorResponse);
		}

		// Save the new admin
		repo.save(adminRequest);

		// Return a JSON response with success message
		Map<String, String> successResponse = new HashMap<>();
		successResponse.put("message", "Admin registered successfully!");
		return ResponseEntity.ok(successResponse);
	}

}
