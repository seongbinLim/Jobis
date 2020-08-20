package com.web.blog.service;

import java.util.List;
import java.util.Optional;

import com.web.blog.dao.board.BoardDao;
import com.web.blog.dao.board.CommentDao;
import com.web.blog.model.board.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentDao commentDao;
    
    @Autowired
    BoardDao boardDao;

    public List<Comment> getCommentsList(int boardno) {
        return commentDao.findByBoardno(boardno);
    }

    public Optional<Comment> deleteComment(int commentno) {
        return commentDao.deleteByCommentno(commentno);
    }

    public Comment createComment(Comment comment) {
        return commentDao.save(comment);
    }

    public Comment updateComment(Comment comment) {
        return commentDao.save(comment);
    }
}