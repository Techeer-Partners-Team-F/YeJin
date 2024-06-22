package kr.co.techeerpartners.board.service;

import kr.co.techeerpartners.board.entity.Board;
import kr.co.techeerpartners.board.entity.Bookmark;
import kr.co.techeerpartners.board.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {
    @Autowired
    private BookmarkRepository bookmarkRepository;

    public void addBookmark(Board board) {
        Bookmark bookmark = new Bookmark();
        bookmark.setBoard(board);
        bookmarkRepository.save(bookmark);
    }

    public void removeBookmark(Board board) {
        List<Bookmark> bookmarks = bookmarkRepository.findByBoard(board);
        if (!bookmarks.isEmpty()) {
            bookmarkRepository.deleteAll(bookmarks);
        }
    }

    public List<Bookmark> getAllBookmarks() {
        return bookmarkRepository.findAll();
    }
}
