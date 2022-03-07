package dev.kalmh.basic.entity;

import javax.persistence.*;

@Entity
@Table(name = "post")
public class PostEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;
    private String content;

    //꼭 있어야 하는가?
    // - login기능이 없으므로 게시글마다 password가 있다고 가정
    private String password;

    @ManyToOne(
            targetEntity = BoardEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @ManyToOne(
            targetEntity = UserEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private UserEntity writer;

    public PostEntity() {}

    public PostEntity(String title, String content, UserEntity writer, String password, BoardEntity boardEntity) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.boardEntity = boardEntity;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public BoardEntity getBoardEntity() {
        return boardEntity;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity getWriter() {
        return writer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBoardEntity(BoardEntity boardEntity) {
        this.boardEntity = boardEntity;
    }

    public void setWriter(UserEntity writer) {
        this.writer = writer;
    }
}
