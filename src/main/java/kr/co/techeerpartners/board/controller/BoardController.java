package kr.co.techeerpartners.board.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.techeerpartners.board.entity.Board;
import kr.co.techeerpartners.board.entity.Bookmark;
import kr.co.techeerpartners.board.entity.Like;
import kr.co.techeerpartners.board.service.BoardService;
import kr.co.techeerpartners.board.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@Api(tags = "게시판 API")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping("/board/write")
    @ApiOperation(value = "게시물 작성", notes = "게시물 작성")
    public String boardWriteForm() {
        return "boardwrite";
    }

    @PostMapping("/board/writeProcess")
    @ApiOperation(value = "게시물 작성 처리", notes = "게시물 작성 처리")
    public String boardWriteProcess(Board board) {

        boardService.write(board);

        return "redirect:/board/list";
    }

    @GetMapping("/board/list")
    @ApiOperation(value = "게시물 목록 조회", notes = "전체 게시물 조회")
    public String boardlist(Model model) {

        model.addAttribute("list", boardService.boardList());

        return "boardlist";
    }

    @GetMapping("/board/view") // localhost:8080/board/view?id=1
    @ApiOperation(value = "게시물 상세 조회", notes = "게시물 상세 조회")
    public String boardView(Model model, @RequestParam("id") Integer id) {

        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    @ApiOperation(value = "게시물 삭제", notes = "게시물 삭제")
    public String boardDelete(@RequestParam("id") Integer id) {

        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    @ApiOperation(value = "게시물 수정", notes = "게시물 수정")
    public String boardModify(@PathVariable("id") Integer id,
                              Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    @ApiOperation(value = "게시물 수정 처리", notes = "게시물 수정 처리")
    public String boardUpdate(@PathVariable("id") Integer id, Board board) {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        return "redirect:/board/list";
    }

    @PostMapping("/board/like/{id}")
    @ApiOperation(value = "게시물 좋아요", notes = "게시물 좋아요")
    public String likePost(@PathVariable("id") Integer id) {
        Board board = boardService.boardView(id);
        Like like = new Like();
        like.setBoard(board);

        if (board.getLikes() == null) {
            board.setLikes(new ArrayList<>());
        }
        board.getLikes().add(like);
        boardService.save(board);

        return "redirect: /board/view?id=" + id;
    }

    @PostMapping("/board/dislike/{id}")
    @ApiOperation(value = "좋아요 취소", notes = "좋아요 취소")
    public String dislikePost(@PathVariable("id") Integer id) {
        Board board = boardService.boardView(id);

        Like likeToRemove = null;
        if (board.getLikes() != null) {
            for (Like like : board.getLikes()) {
                if (like.getBoard().getId().equals(id)) {
                    likeToRemove = like;
                    break;
                }
            }
        }
        if (likeToRemove != null) {
            board.getLikes().remove(likeToRemove);
        }

        boardService.save(board);

        return "redirect: /board/view?id=" + id;
    }
    @GetMapping("/bookmarks")
    @ApiOperation(value = "북마크 목록 조회", notes = "북마크 목록 조회")
    public String viewBookmarks(Model model) {
        List<Bookmark> bookmarks = bookmarkService.getAllBookmarks();
        model.addAttribute("bookmarks", bookmarks);
        return "bookmarks";
    }

    public String listBookmarks(Model model) {
        List<Bookmark> bookmarks = bookmarkService.getAllBookmarks();
        model.addAttribute("bookmarks", bookmarks);
        return "boardlist";
    }

    @PostMapping("/board/bookmark/{id}")
    @ApiOperation(value = "북마크 추가", notes = "북마크 추가")
    public String bookmarkPost(@PathVariable("id") Integer id) {
        Board board = boardService.boardView(id);
        bookmarkService.addBookmark(board);

        return "redirect:/board/view?id=" + id;
    }

    @PostMapping("/board/unbookmark/{id}")
    @ApiOperation(value = "북마크 취소", notes = "북마크 취소")
    public String unbookmarkPost(@PathVariable("id") Integer id) {
        Board board = boardService.boardView(id);
        bookmarkService.removeBookmark(board);

        return "redirect:/board/view?id=" + id;
    }
}


