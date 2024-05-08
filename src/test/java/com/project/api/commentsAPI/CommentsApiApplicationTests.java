package com.project.api.commentsAPI;

import com.project.api.commentsAPI.controller.CommentController;
import com.project.api.commentsAPI.dto.CommentRequest;
import com.project.api.commentsAPI.model.Comment;
import com.project.api.commentsAPI.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class CommentsApiApplicationTests {


		private CommentService commentService = mock(CommentService.class);
		private CommentController commentController = new CommentController(commentService);

		@Test
		void testGetAllComments() {
			List<Comment> comments = Arrays.asList(new Comment(), new Comment());
			when(commentService.getAllComments()).thenReturn(comments);


			List<Comment> result = commentController.getAllComments();


			assertEquals(comments, result);
		}

		@Test
		void testSearchCommentsByUsername() {

			String username = "user1";
			List<Comment> comments = Arrays.asList(new Comment(), new Comment());
			when(commentService.getCommentsByUsername(username)).thenReturn(comments);


			List<Comment> result = commentController.searchComments(username, null);


			assertEquals(comments, result);
		}

		@Test
		void testSearchCommentsByDate() {
			LocalDate date = LocalDate.now();
			LocalDateTime startOfDay = date.atStartOfDay();
			List<Comment> comments = Arrays.asList(new Comment(), new Comment());
			when(commentService.getCommentsByDate(startOfDay)).thenReturn(comments);


			List<Comment> result = commentController.searchComments(null, date);


			assertEquals(comments, result);
		}

		@Test
		void testAddComment() {
			CommentRequest commentRequest = new CommentRequest();
			Comment comment = new Comment();
			when(commentService.addComment(commentRequest)).thenReturn(comment);


			ResponseEntity<String> response = commentController.addComment(commentRequest);


			assertEquals(HttpStatus.CREATED, response.getStatusCode());
			assertEquals("Success: Comment created successfully", response.getBody());
		}

		@Test
		void testUpdateComment() {
			Long id = 1L;
			Comment comment = new Comment();
			when(commentService.updateComment(id, comment)).thenReturn(comment);


			ResponseEntity<String> response = commentController.updateComment(id, comment);


			assertEquals(HttpStatus.CREATED, response.getStatusCode());
			assertEquals("Success: Comment updated successfully", response.getBody());
		}

		@Test
		void testDeleteComment() {
			Long id = 1L;

			ResponseEntity<String> response = commentController.deleteComment(id);

			assertEquals(HttpStatus.CREATED, response.getStatusCode());
			assertEquals("Success: Comment deleted successfully", response.getBody());
		}
	}



