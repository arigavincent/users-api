package com.ariga.users_api.dto;
import jakarta.validation.constraints.*;
public record CreateUserRequest(
   @NotBlank @Size (max=100) String name,
   @NotBlank@Email  String email,
   @NotNull @Positive  Integer age
) {
    
}
