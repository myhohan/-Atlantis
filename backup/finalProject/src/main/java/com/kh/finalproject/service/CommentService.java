package com.kh.finalproject.service;

import java.util.List;

import com.kh.finalproject.dto.Comment;

public interface CommentService {
		/**
		 * 
		 * @param boardNo
		 * @return
		 */
		List<Comment> select(int boardNo);
		
		/**
		 * 
		 * @param comment
		 * @return
		 */
		int insert(Comment comment);
		
		/**
		 * 
		 * @param commentNo
		 * @return
		 */
		int delete(int commentNo);
		
		/**
		 * 
		 * @param comment
		 * @return
		 */
		int update(Comment comment);
}
