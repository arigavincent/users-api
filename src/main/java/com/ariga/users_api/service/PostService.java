package com.ariga.users_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ariga.users_api.dto.CreatePostRequest;
import com.ariga.users_api.dto.PostResponse;
import com.ariga.users_api.entities.Post;
import com.ariga.users_api.entities.User;
import com.ariga.users_api.exceptions.UserNotFoundException;
import com.ariga.users_api.mapper.PostMapper;
import com.ariga.users_api.repository.PostRepository;
import com.ariga.users_api.repository.UserRepository;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostResponse createPost(Long userId, CreatePostRequest request) {
        User user = findUserOrThrow(userId);

        Post post = postMapper.toPostEntity(request, user);
        Post saved = postRepository.save(post);
        return postMapper.toPostResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getPostsByUser(Long userId, Pageable pageable) {
        ensureUserExists(userId);
        Page<Post> posts = postRepository.findByUserId(userId, pageable);
        return posts.map(postMapper::toPostResponse);
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void ensureUserExists(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
    }
}
