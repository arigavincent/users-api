package com.ariga.users_api.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ariga.users_api.dto.CreateUserRequest;
import com.ariga.users_api.dto.PatchUser;
import com.ariga.users_api.dto.UpdateUser;
import com.ariga.users_api.dto.UserResponse;
import com.ariga.users_api.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody  CreateUserRequest request){
        return  userService.create(request);
    }

    @GetMapping 
    // @ResponseStatus (HttpStatus.OK)
    // public List<UserResponse> getAllUsers(){
    //     return  userService.getAllUsers();
    // }
    public PagedModel<UserResponse> getAllUsers(@RequestParam(required = false) String name, Pageable pageable){
        return new PagedModel<>(userService.getAllUsers(name,pageable));
    }

   @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UpdateUser request) {
        return userService.updateUser(id, request);
    }

    @PatchMapping("/{id}")
    public UserResponse patch(@PathVariable Long id, @Valid @RequestBody PatchUser request) {
        return userService.patchUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
