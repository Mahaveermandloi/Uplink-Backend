package com.linkup.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.linkup.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	

}