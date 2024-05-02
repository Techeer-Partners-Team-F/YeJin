package kr.co.techeerpartners.board.controller;

import kr.co.techeerpartners.board.entity.Board;
import kr.co.techeerpartners.board.entity.Like;
import kr.co.techeerpartners.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm() {
        return "boardwrite";
    }

    @PostMapping("/board/writeProcess")
    public String boardWriteProcess(Board board) {

        boardService.write(board);

        return "redirect:/board/list";
    }

    @GetMapping("/board/list")
    public String boardlist(Model model) {

        model.addAttribute("list", boardService.boardList());

        return "boardlist";
    }

    @GetMapping("/board/view") // localhost:8080/board/view?id=1
    public String boardView(Model model, @RequestParam("id") Integer id) {

        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam("id") Integer id) {

        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id,
                              Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board) {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        return "redirect:/board/list";
    }

    @PostMapping("/board/like/{id}")
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

        // 좋아요를 찾았으면 제거합니다.
        if (likeToRemove != null) {
            board.getLikes().remove(likeToRemove);
        }

        boardService.save(board);

        return "redirect: /board/view?id=" + id;
    }
}


