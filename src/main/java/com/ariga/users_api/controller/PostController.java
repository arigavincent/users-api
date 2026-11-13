package com.ariga.users_api.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ariga.users_api.dto.CreatePostRequest;
import com.ariga.users_api.dto.PostResponse;
import com.ariga.users_api.service.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/{userId}/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@PathVariable Long userId,
                               @Valid @RequestBody CreatePostRequest request) {
        return postService.createPost(userId, request);
    }

    @GetMapping
    public PagedModel<PostResponse> getPosts(@PathVariable Long userId, Pageable pageable) {
        return new PagedModel<>(postService.getPostsByUser(userId, pageable));
    }
}