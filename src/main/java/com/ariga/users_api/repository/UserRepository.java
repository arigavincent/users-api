package com.ariga.users_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ariga.users_api.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String Email);
    // boolean existsById(Long id);
    boolean existsByEmailAndIdNot(String Email,Long Id);
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
