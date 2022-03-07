package dev.kalmh.basic.service;

import dev.kalmh.basic.dao.UserDao;
import dev.kalmh.basic.dto.UserDto;
import dev.kalmh.basic.entity.UserEntity;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDao userDao;

    public UserService(
            @Autowired UserDao userDao
    ) {
        this.userDao = userDao;
    }

    //POST
    public void createUser(UserDto dto) {
        this.userDao.createUser(dto);
    }

    //GET ALL
    public List<UserDto> readUserAll() {
        Iterator<UserEntity> iterator = this.userDao.readUserAll();
        List<UserDto> userDtoList = new ArrayList<>();
        while (iterator.hasNext()) {
            UserEntity userEntity= iterator.next();
            userDtoList.add(new UserDto(
                    Math.toIntExact(userEntity.getId()),
                    userEntity.getLoginId()
            ));
        }
        return userDtoList;
    }

    //GET
    public UserDto readUser(int id) {
        UserEntity userEntity = this.userDao.readUser(id);
        UserDto userDto = new UserDto(
                Math.toIntExact(userEntity.getId()),
                userEntity.getLoginId()
        );
        return userDto;
    }

    //UPDATE
    public void updateUser(int id, UserDto dto) {this.userDao.updateUser(id, dto);}

    //DELETE
    public void deleteUser(int id) {this.userDao.deleteUser(id);}
}
