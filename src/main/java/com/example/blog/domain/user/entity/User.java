package com.example.blog.domain.user.entity;

import com.example.blog.domain.comment.entity.CommentLike;
import com.example.blog.domain.post.entity.PostLike;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userIdenticonUrl;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)    // Enum값 그대로 db에 저장
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLikes;

    @Builder
    private User(String userIdenticonUrl, String username, String password, UserRoleEnum role) {
        this.userIdenticonUrl = userIdenticonUrl;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
