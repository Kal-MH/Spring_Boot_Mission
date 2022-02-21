package dev.kalmh.board.post;

import java.util.List;

public interface PostService {
    int createPost(PostDto dto);
    List<PostDto> readPostAll();
    PostDto readPost(int id);
    boolean updatePost(int id, PostDto dto);
    void deletePost(int id);
}
