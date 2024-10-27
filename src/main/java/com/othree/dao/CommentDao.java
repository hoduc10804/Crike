package com.othree.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.othree.entity.Comment;

public interface CommentDao extends JpaRepository<Comment, Integer>{

}
