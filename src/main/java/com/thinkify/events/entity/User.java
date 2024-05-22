package com.thinkify.events.entity;

import com.thinkify.events.model.object.Role;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobileNumber;
    @Enumerated
    private Role role;
}
