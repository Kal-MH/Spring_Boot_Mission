package dev.kalmh.board.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceSimple implements PostService{
    private static final Logger logger = LoggerFactory.getLogger(PostServiceSimple.class);
    private final PostRepository postRespository;

    public PostServiceSimple (
            @Autowired PostRepository postRespository
    ) {
        this.postRespository = postRespository;
    }

    @Override
    public int createPost(PostDto dto) {
        // TODO check validity
        if (!this.postRespository.save(dto)) {
            throw new RuntimeException("save failed");
        }
        //현재 post primary key 반환
        return this.postRespository.findAll().size() - 1;
    }

    @Override
    public List<PostDto> readPostAll() {
        return this.postRespository.findAll();
    }

    @Override
    public PostDto readPost(int id) {
        return this.postRespository.findById(id);
    }

    @Override
    public boolean updatePost(int id, PostDto dto) {
        return this.postRespository.update(id, dto);
    }

    @Override
    public void deletePost(int id) {
        this.postRespository.delete(id);
    }
}
