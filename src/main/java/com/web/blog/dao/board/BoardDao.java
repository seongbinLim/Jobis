package com.web.blog.dao.board;

import java.util.List;
import java.util.Optional;

import com.web.blog.model.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BoardDao extends JpaRepository<Board, String> {
    @Transactional
    public Optional<Board> deleteByBoardno(int boardno);

    public Board getBoardByBoardno(int boardno);
    public Board save(Board board);

    public List<Board> findByBoardno(int boardno);
    public List<Board> findByUid(String uid);
    public List<Board> findBySubjectLike(String subject);
    public List<Board> findTop10ByOrderByLikesDesc();
    
    //@Query("SELECT uid FROM Board")
	//List<Board> myBoard()
}