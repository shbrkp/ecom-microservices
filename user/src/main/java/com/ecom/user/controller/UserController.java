package com.ecom.user.controller;

import com.ecom.user.dto.UserRequest;
import com.ecom.user.dto.UserResponse;
import com.ecom.user.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private List<UserResponse> userList = new ArrayList<>();

    @Autowired
    Environment env;

    private final MongoTemplate mongoTemplate;

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        System.out.println("getAllUsers");
        userList = userService.getAllusers();
        if(userList.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(userList);

    }

    @PostConstruct
    public void checkDb() {
        System.out.println("Connected DB: " + mongoTemplate.getDb().getName());
        System.out.println("Connected DB: " + mongoTemplate.getDb().getName());
       // System.out.println("Connected Host: " + mongoTemplate.getDb()..getMongoClient().getClusterDescription());
    }
    @PostConstruct
    public void checkUri() {
        System.out.println("Mongo URI from Spring: " +
                env.getProperty("spring.data.mongodb.uri"));


    }
    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){


        userService.adduser(userRequest);
        return ResponseEntity.ok("User added successfully");
    }

   @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){

        /*Optional<User> user = userService.fetchUser(id);
        if(user.isEmpty()) sample changes in new branch
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userService.fetchUser(id));*/


        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequest updatedUserrequest,
                                             @PathVariable String id){
        System.out.println("updatedUser----"+updatedUserrequest.getFirstName());
        boolean updatedUserBool = userService.updateUser(updatedUserrequest,id);
        System.out.println("updatedUserBool----"+updatedUserBool);
        if(updatedUserBool)
            return ResponseEntity.ok("User updated successfully");
        return ResponseEntity.notFound().build();
    }
}
