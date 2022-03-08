package dev.kalmh.challenge.service;

import dev.kalmh.challenge.dao.PostDao;
import dev.kalmh.challenge.dto.PostDto;
import dev.kalmh.challenge.entity.PostEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final PostDao postDao;

    public PostService(
            @Autowired PostDao postDao
    ) {
        this.postDao = postDao;
    }

    //POST
    public void createPost(int boardId, PostDto dto) {
        this.postDao.createPost(boardId, dto);
    }

    //GET ALL
    public List<PostDto> readPostAll() {
        Iterator<PostEntity> iterator = this.postDao.readPostAll();
        List<PostDto> postDtoList = new ArrayList<>();
        while (iterator.hasNext()) {
            PostEntity postEntity = iterator.next();
            postDtoList.add(new PostDto(
                    Math.toIntExact(postEntity.getId()),
                    postEntity.getTitle(),
                    postEntity.getContent(),
                    postEntity.getPassword(),
                    Math.toIntExact(postEntity.getWriter().getId()),
                    Math.toIntExact(postEntity.getBoardEntity().getId())
            ));
        }
        return postDtoList;
    }
    public List<PostDto> readPostAll(int boardId) {
        List<PostEntity> postEntityList = this.postDao.readPostAll(boardId);
        List<PostDto> postDtoList = new ArrayList<>();
        for (PostEntity postEntity: postEntityList) {
            postDtoList.add(new PostDto(
                    Math.toIntExact(postEntity.getId()),
                    postEntity.getTitle(),
                    postEntity.getContent(),
                    postEntity.getPassword(),
                    Math.toIntExact(postEntity.getWriter().getId()),
                    Math.toIntExact(postEntity.getBoardEntity().getId())
            ));
        }
        return postDtoList;
    }

    //GET
    public PostDto readPost(int id) {
        PostEntity postEntity = this.postDao.readPost(id);
        return  new PostDto(
                Math.toIntExact(postEntity.getId()),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getPassword(),
                Math.toIntExact(postEntity.getWriter().getId()),
                Math.toIntExact(postEntity.getBoardEntity().getId())
        );
    }
    public PostDto readPost(int boardId, int idx) {
        PostEntity postEntity = this.postDao.readPost(boardId, idx);
        return  new PostDto(
                Math.toIntExact(postEntity.getId()),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getPassword(),
                Math.toIntExact(postEntity.getWriter().getId()),
                Math.toIntExact(postEntity.getBoardEntity().getId())
        );
    }

    //UPDATE
    public void updatePost(int id, PostDto dto) {this.postDao.updatePost(id, dto);}
    public void updatePost(int boardId, int idx, PostDto dto) {this.postDao.updatePost(boardId, idx, dto);}

    //DELETE
    public void deletePost(int id, PostDto dto) {this.postDao.deletePost(id, dto);}
    public void deletePost(int boardId, int idx, PostDto dto) {this.postDao.deletePost(boardId, idx, dto);}
}
