package com.sparta.springbootblogproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {

    private Long id;

    private String username;

    private String password;

    @Enumerated(value= EnumType.STRING)
    private UserRoleEnum role;

    //private List<PostLike> postLikes;


}
