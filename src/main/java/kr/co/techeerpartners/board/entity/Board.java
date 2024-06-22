package kr.co.techeerpartners.board.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    private String title;
    private String content;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks;
}
