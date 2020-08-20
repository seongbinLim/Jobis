package com.web.blog.dao.board;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.web.blog.model.board.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, String> {
    @Transactional
    public Optional<Comment> deleteByCommentno(int commentno);
    public Comment save(Comment comment);
    public List<Comment> findByBoardno(int boardno);
    public List<Comment> findByCommentno(int commentno);
}