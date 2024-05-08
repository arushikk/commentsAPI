package com.project.api.commentsAPI.service;
import com.project.api.commentsAPI.dto.CommentRequest;
import com.project.api.commentsAPI.dto.ResourceNotFoundException;
import com.project.api.commentsAPI.model.Comment;
import com.project.api.commentsAPI.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByUsername(String username) {
        return commentRepository.findByCommentedBy(username);
    }

    public List<Comment> getCommentsByDate(LocalDateTime date) {

        return commentRepository.findByDateOfComment(date);
    }

    public Comment addComment(CommentRequest commentRequest) {



        return commentRepository.save(mapToComment(commentRequest));
    }

    public Comment updateComment(Long id, Comment updatedComment) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        existingComment.setCommentedBy(updatedComment.getCommentedBy());
        existingComment.setText(updatedComment.getText());
        existingComment.setDateOfComment(updatedComment.getDateOfComment());

        return commentRepository.save(existingComment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment mapToComment(CommentRequest commentRequest){
        Comment comment = Comment.builder()
                .commentedBy(commentRequest.getCommentedBy())
                .text(commentRequest.getText())
                        .dateOfComment(commentRequest.getDateOfComment())
                .build();

        return comment;
    }
}

