package com.web.blog.controller.board;

import java.util.List;
import java.util.Optional;

import com.web.blog.model.board.Board;
import com.web.blog.model.board.Comment;
import com.web.blog.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @ApiOperation(value = "댓글 리스트", response = List.class)
	@GetMapping("/list/{boardno}")
	public ResponseEntity<List<Comment>> getCommentsList(@PathVariable int boardno) throws Exception {
        List<Comment> commentList = commentService.getCommentsList(boardno);
        
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }
    
    @ApiOperation(value = "댓글 작성", response = List.class)
	@PostMapping("/create")
	public ResponseEntity<String> createComment(@RequestBody Comment comment) throws Exception {
        if(commentService.createComment(comment) != null) 
            return new ResponseEntity<String>("success", HttpStatus.OK);

        return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "댓글 삭제", response = List.class)
	@DeleteMapping("/{commentno}")
	public ResponseEntity<String> deleteComment(@PathVariable int commentno) throws Exception {
        Optional<Comment> isDeleted = commentService.deleteComment(commentno);
        try {
            if(!isDeleted.isEmpty())
                return new ResponseEntity(HttpStatus.OK);

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } catch(Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "댓글 수정", response = List.class)
	@PutMapping("/modify")
	public ResponseEntity<String> updateComment(@RequestBody Comment comment) throws Exception {
        if (commentService.updateComment(comment) != null) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
    }
}