package com.project.api.commentsAPI.controller;


import com.project.api.commentsAPI.dto.CommentRequest;
import com.project.api.commentsAPI.model.Comment;
import com.project.api.commentsAPI.service.CommentService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v2/comments")
public class CommentController {


    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService=commentService;
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }


    @GetMapping("/search")
    public List<Comment> searchComments(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date)  {

        if (username != null) {
            return commentService.getCommentsByUsername(username);
        } else if (date != null) {
            LocalDateTime startOfDay = date.atStartOfDay();
            return commentService.getCommentsByDate(startOfDay);
        } else {

            return Collections.emptyList();
        }
    }
    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody CommentRequest commentRequest) {
        Comment comment =commentService.addComment(commentRequest);

        String successMessage = "Success: Comment created successfully";

        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody Comment comment) {

        Comment commentUpdated = commentService.updateComment(id, comment);
        String successMessage = "Success: Comment updated successfully";

        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        String successMessage = "Success: Comment deleted successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            errors.add(violation.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
