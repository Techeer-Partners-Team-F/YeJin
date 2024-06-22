package kr.co.techeerpartners.board.repository;

import kr.co.techeerpartners.board.entity.Board;
import kr.co.techeerpartners.board.entity.Bookmark;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    List<Bookmark> findByBoard(Board board);
    Bookmark findByBoardId(Integer boardId);
}
