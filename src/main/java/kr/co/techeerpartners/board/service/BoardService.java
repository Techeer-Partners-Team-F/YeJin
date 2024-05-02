package kr.co.techeerpartners.board.service;

import kr.co.techeerpartners.board.entity.Board;
import kr.co.techeerpartners.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    // 글 작성
    public void write(Board board) {

        boardRepository.save(board);

    }

    // 게시물 리스트
    public List<Board> boardList() {

        return  boardRepository.findAll();
    }

    // 특정 게시물 불러오기
    public  Board boardView(Integer id) {

        return  boardRepository.findById(id).get();
    }

    // 특정 게시물 삭제
    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);
    }
}
