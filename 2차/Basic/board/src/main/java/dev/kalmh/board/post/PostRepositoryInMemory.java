package dev.kalmh.board.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepositoryInMemory implements PostRepository{
    private static final Logger logger = LoggerFactory.getLogger(PostRepositoryInMemory.class);
    private final List<PostDto> postList;
    public PostRepositoryInMemory() {
        this.postList = new ArrayList<>();
    }

    @Override
    public boolean save(PostDto dto) {
        //primary key 수동으로 설정.
        dto.setId(postList.size());
        return this.postList.add(dto);
    }

    @Override
    public List<PostDto> findAll() {
        return this.postList;
    }

    @Override
    public PostDto findById(int id) {
        return this.postList.get(id);
    }

    @Override
    public boolean update(int id, PostDto dto) {
        PostDto targetPost = this.postList.get(id);

        //writer, password는 변경 불가하다고 설정
        // - password를 이용해서 title, content 변경
        if (!dto.getPassword().equals(targetPost.getPassword()))
            return false;

        if (targetPost.getTitle() != null)
            targetPost.setTitle(dto.getTitle());
        if (targetPost.getContent() != null)
            targetPost.setContent(dto.getTitle());
        this.postList.set(id, targetPost);
        return true;
    }

    @Override
    public boolean delete(int id) {
        this.postList.remove(id);
        return true;
    }
}
