package com.web.blog.service;

import java.util.List;
import java.util.Optional;

import com.web.blog.dao.board.BoardDao;
import com.web.blog.dao.board.UserlikeDao;
import com.web.blog.model.board.Board;
import com.web.blog.model.board.Userlike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {
    @Autowired
    BoardDao boardDao;
    
    @Autowired
    UserlikeDao userlikeDao;

    public List<Board> getListOfBoard() {
        return boardDao.findAll();
    }

    public Board getBoard(int boardno) {
        return boardDao.getBoardByBoardno(boardno);
    }

    public Optional<Board> deleteBoard(int boardno) {
        return boardDao.deleteByBoardno(boardno);
    }

    public Board createBoard(Board board) {
        return boardDao.save(board);
    }

    public Board updateBoard(Board board) {
        return boardDao.save(board);
    }
    
    public List<Board> findByUid(String uid) {
        return boardDao.findByUid(uid);
    }

    public List<Board> findBySubject(String subject) {
        return boardDao.findBySubjectLike(subject);
    }

    
    // 페이지네이션
    public Page<Board> findBoardByPageRequest(Integer page){
        PageRequest pageRequest = PageRequest.of(page, 10);
        System.out.println(boardDao.findAll(pageRequest));
        return boardDao.findAll(pageRequest);

    }

    //좋아요순 리스트
    public List<Board> getListOfBoardOrderByLikes() {
        //return boardDao.findAll(Sort.by(Sort.Direction.DESC, "likes"));
        return boardDao.findTop10ByOrderByLikesDesc();
    }

    //좋아요 클릭
    @Transactional
    public boolean clickBoardLike(int boardNo, String uid) throws Exception {
        //User_like.boardno && User_like.uid 체크
        //Board.boardno 로 게시글 like 수 조정
        
        boolean clicked = true;
        List<Userlike> userlikeList = userlikeDao.findByBoardnoAndUid(boardNo, uid);
        List<Board> boardlist = boardDao.findByBoardno(boardNo);

        if(boardlist.isEmpty()) {
            System.out.println("없는 boardno 넘겨줌");
            throw new Exception();
        }
        if(userlikeList.isEmpty()) {
            System.out.println("좋아요 상태로 변경하자");
            clicked = false;
        }

        if(clicked) { //클릭되어 있으면 취소
            Board board = boardDao.getBoardByBoardno(boardNo);
            int updateLikes = board.getLikes()-1;
            board.setLikes(updateLikes);
            boardDao.save(board);

            System.out.println("save 성공");
            //userlikeDao.deleteByBoardno(boardNo);
            userlikeDao.deleteByBoardnoAndUid(boardNo, uid);
        } else { //클릭되어 있지 않으면 생성
            Board board = boardDao.getBoardByBoardno(boardNo);
            int updateLikes = board.getLikes()+1;
            board.setLikes(updateLikes);
            boardDao.save(board);

            Userlike userlike = new Userlike();
            userlike.setBoardno(boardNo);
            userlike.setUid(uid);
            userlikeDao.save(userlike);
        }
        
        return clicked;
    }

    //좋아요여부 확인
    public boolean likeCheck(int boardno, String uid) throws Exception {
        List<Userlike> userlikeList = userlikeDao.findByBoardnoAndUid(boardno, uid);
        if(userlikeList.isEmpty())
            return false;
        return true;
    }


}
