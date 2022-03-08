package dev.kalmh.basic.dao;

import dev.kalmh.basic.dto.UserDto;
import dev.kalmh.basic.entity.PostEntity;
import dev.kalmh.basic.entity.UserEntity;
import dev.kalmh.basic.repository.PostRepository;
import dev.kalmh.basic.repository.UserRepository;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserDao(
            @Autowired UserRepository userRepository,
            @Autowired PostRepository postRepository
    ) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    //POST
    public void createUser(UserDto dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLoginId(dto.getLoginId());
        this.userRepository.save(userEntity);
    }

    //GET ALL
    public Iterator<UserEntity> readUserAll() {
        return this.userRepository.findAll().iterator();
    }

    //GET
    public UserEntity readUser(int id) {
        Optional<UserEntity> userEntity = this.userRepository.findById((long)id);
        if (userEntity.isEmpty()) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
        return userEntity.get();
    }

    //UPDATE
    public void updateUser(int id, UserDto dto) {
        Optional<UserEntity> targetEntity = this.userRepository.findById((long)id);
        if (targetEntity.isEmpty()) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
        UserEntity userEntity = targetEntity.get();
        userEntity.setLoginId(dto.getLoginId());
        this.userRepository.save(userEntity);
    }

    //DELETE
    public void deleteUser(int id) {
        Optional<UserEntity> targetEntity = this.userRepository.findById((long)id);
        if (targetEntity.isEmpty()) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
        List<PostEntity> postEntityList = targetEntity.get().getPostEntityList();
        for(PostEntity post : postEntityList) {
            this.postRepository.delete(post);
        }
        this.userRepository.delete(targetEntity.get());
    }
}
