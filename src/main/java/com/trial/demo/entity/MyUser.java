package com.trial.demo.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name="users")
public class MyUser {


    @Id
    private String username;
    private String password;
    private String roles;

}