package kr.co.techeerpartners.board.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;

@Entity
@Data
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
