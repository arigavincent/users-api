package com.ariga.users_api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ariga.users_api.dto.CreateUserRequest;
import com.ariga.users_api.dto.PatchUser;
import com.ariga.users_api.dto.UpdateUser;
import com.ariga.users_api.dto.UserResponse;
import com.ariga.users_api.entities.User;
import com.ariga.users_api.exceptions.EmailAlreadyExistsException;
import com.ariga.users_api.exceptions.UserNotFoundException;
import com.ariga.users_api.mapper.UserMapper;
import com.ariga.users_api.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }
        User savedUser = userRepository.save(userMapper.toEntity(request));
        return userMapper.toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        return userMapper.toResponse(findUserOrThrow(id));
    }

    @Transactional(readOnly = true)
    // public List<UserResponse> getAllUsers() {
    // return userMapper.responses(userRepository.findAll());
    // }
    public Page<UserResponse> getAllUsers(String name, Pageable pageable) {
        Page<User> users = (name == null || name.isBlank())
                ? userRepository.findAll(pageable)
                : userRepository.findByNameContainingIgnoreCase(name, pageable);
        return users.map(userMapper::toResponse);
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUser request) {
        User user = findUserOrThrow(id);
        ensureEmailAvailable(request.email(), id);
        userMapper.applyUpdate(user, request);
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse patchUser(Long id, PatchUser request) {
        User user = findUserOrThrow(id);
        if (request.email() != null) {
            ensureEmailAvailable(request.email(), id);
        }
        userMapper.applyPatch(user, request);
        return userMapper.toResponse(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(findUserOrThrow(id));
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void ensureEmailAvailable(String email, Long id) {
        if (userRepository.existsByEmailAndIdNot(email, id)) {
            throw new EmailAlreadyExistsException(email);
        }
    }
}
