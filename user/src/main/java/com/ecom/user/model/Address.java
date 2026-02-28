package com.ecom.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Address {
    @Id
    private Long id;

    private String street;
    private String city;
    private String state;
    private String country;


}
