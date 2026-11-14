package com.ariga.users_api.mapper;

import org.springframework.stereotype.Component;

import com.ariga.users_api.dto.CreatePostRequest;
import com.ariga.users_api.dto.PostResponse;
import com.ariga.users_api.entities.Post;
import com.ariga.users_api.entities.User;

@Component 
public class PostMapper {
    public Post toPostEntity(CreatePostRequest request,User user){
        Post post = new  Post();
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setUser(user);
        return post;
    }

    public PostResponse toPostResponse(Post post){
        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getUser().getId(),
            post.getUser().getName(),
            post.getCreatedAt()
        );
    }
    
}
