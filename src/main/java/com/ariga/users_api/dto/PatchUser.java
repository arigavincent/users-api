package com.ariga.users_api.dto;
import jakarta.validation.constraints.*;
public record PatchUser(
   @Size (max=100) String name,
   @Email  String email,
   @Positive  Integer age
) {
    
}
