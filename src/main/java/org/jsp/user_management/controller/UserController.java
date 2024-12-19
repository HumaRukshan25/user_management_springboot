package org.jsp.user_management.controller;


import org.jsp.user_management.entity.User;
import org.jsp.user_management.responsestructure.ResponseStructure;
import org.jsp.user_management.service.UserService;
import org.jsp.user_management.util.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

 
    @Operation(summary="This api will save the user entity",description="This api will accept a user json object")
    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
    	
        User savedUser = service.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @Operation(summary="This api will find the user by id",description="This api will find a user")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="user found successfully"),@ApiResponse(responseCode="404",description="invalid user id")})
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> user = service.getUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary="This api will find all the users",description="This api will give users")
    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(service.findAllUsers());
    }

    // Login
    @Operation(summary="This api will do  login and validation ",description="This api will do login validation by  accepting a requestbody ie AuthUser  with username and password")
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<User>> login(@RequestBody AuthUser authUser) {
        return service.login(authUser);
    }
    
    @PatchMapping("/{id}/otp/{otp}")
    public ResponseEntity<?> verifyOtp(@PathVariable int id,@PathVariable int otp){
    	return service.verifyOtp(id,otp);
    	
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = service.getUserByEmail(email);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    
    
}


   
