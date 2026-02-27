package com.ecom.user.dto;

import com.ecom.user.model.UserRole;
import lombok.Data;

@Data
public class UserResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private com.ecom.user.model.UserRole role;
    private AddressDTO addressDTO;
}
