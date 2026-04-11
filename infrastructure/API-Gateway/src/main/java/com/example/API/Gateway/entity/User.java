package com.example.API.Gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table("users")
public class User {
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
}
