package com.ariga.users_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ariga.users_api.entities.Post;

public interface PostRepository extends JpaRepository<Post,Long>{
    Page<Post> findByUserId(Long userId,Pageable pageable);
}
