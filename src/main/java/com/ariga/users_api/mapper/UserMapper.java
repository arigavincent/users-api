package com.ariga.users_api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ariga.users_api.dto.CreateUserRequest;
import com.ariga.users_api.dto.PatchUser;
import com.ariga.users_api.dto.UpdateUser;
import com.ariga.users_api.dto.UserResponse;
import com.ariga.users_api.entities.User;
@Component 
public class UserMapper {
    public User toEntity(CreateUserRequest userRequest){
        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setAge(userRequest.age());
        return user;
    }

    public UserResponse toResponse(User userResponse){
        return new UserResponse(
            userResponse.getId(),
            userResponse.getName(),
            userResponse.getEmail(),
            userResponse.getAge(),
            userResponse.getCreatedAt(),
            userResponse.getUpdatedAt()
        );
    }
    
    public List<UserResponse> responses (List<User> users){
        return users.stream().map(this::toResponse).toList();
    }

    public void applyUpdate(User user, UpdateUser updateUser){
        user.setName(updateUser.name());
        user.setEmail(updateUser.email());
        user.setAge(updateUser.age());

    }

    public void applyPatch(User user,PatchUser patchUser){
        if(patchUser.name() !=null){
            user.setName(patchUser.name());
        }
        if(patchUser.email() !=null){
            user.setEmail(patchUser.email());
        }
        if(patchUser.age() !=null){
            user.setAge(patchUser.age());
        }
    }
}
