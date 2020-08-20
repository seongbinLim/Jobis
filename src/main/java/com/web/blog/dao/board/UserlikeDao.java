package com.web.blog.dao.board;

import java.util.List;
import java.util.Optional;
import com.web.blog.model.board.Userlike;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserlikeDao extends JpaRepository<Userlike, String>{
    public List<Userlike> findByBoardnoAndUid(int boardno, String uid);
    public List<Userlike> findByBoardno(int boardno);
    public Optional<Userlike> deleteByBoardnoAndUid(int boardno, String uid);
    public Optional<Userlike> deleteByBoardno(int boardno);
    public boolean existsByBoardno(int boardno);
    public Userlike save(Userlike userlike);
}