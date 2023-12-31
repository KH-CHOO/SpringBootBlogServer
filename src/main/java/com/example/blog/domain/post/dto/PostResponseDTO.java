package com.example.blog.domain.post.dto;

import com.example.blog.domain.comment.dto.CommentResponseDTO;
import com.example.blog.domain.comment.entity.Comment;
import com.example.blog.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
@Schema(description = "게시글 응답 DTO")
@Getter
public class PostResponseDTO {

    private final Long postId;

    private final String postImageUrl;

    private final String title;

    private final String username;

    private final String userIdenticonUrl;

    private final String content;

    private final LocalDateTime createdDate;

    private List<CommentResponseDTO> commentList;

    private int likeCount;

    public PostResponseDTO(Post post){
        this.postId = post.getId();
        this.postImageUrl = post.getPostImageUrl();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.userIdenticonUrl = post.getUser().getUserIdenticonUrl();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        if (!(post.getCommentList() == null)) setCommentList(post.getCommentList());
        this.likeCount = post.getLikeCount();
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList.stream().map(CommentResponseDTO::new).toList();
    }
}
