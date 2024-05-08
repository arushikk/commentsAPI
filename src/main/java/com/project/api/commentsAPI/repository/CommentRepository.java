package com.project.api.commentsAPI.repository;

import com.project.api.commentsAPI.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCommentedBy(String username);

    @Query("SELECT c FROM Comment c WHERE DATE(c.dateOfComment) = DATE(:date)")
    List<Comment> findByDateOfComment(@Param("date") LocalDateTime date);
}
