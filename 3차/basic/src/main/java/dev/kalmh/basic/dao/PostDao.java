package dev.kalmh.basic.dao;

import dev.kalmh.basic.dto.PostDto;
import dev.kalmh.basic.entity.PostEntity;
import dev.kalmh.basic.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.Optional;

@Repository
public class PostDao {
    private static final Logger logger = LoggerFactory.getLogger(PostDao.class);
    private final PostRepository postRepository;
    public PostDao(
            @Autowired PostRepository postRepository
    ) {
        this.postRepository = postRepository;
    }

    //POST

    //GET ALL

    //GET

    //UPDATE

    //DELETE
}
