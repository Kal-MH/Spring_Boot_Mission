package dev.kalmh.basic.service;

import dev.kalmh.basic.controller.dto.UserDto;
import dev.kalmh.basic.entity.AreaEntity;
import dev.kalmh.basic.entity.UserEntity;
import dev.kalmh.basic.repository.AreaRepository;
import dev.kalmh.basic.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;

    public UserService(
            UserRepository userRepository,
            AreaRepository areaRepository
    ) {
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
    }

    public UserDto readUser(Long id) {
        Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);
        if (userEntityOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new UserDto(userEntityOptional.get());
    }
    public List<UserDto> readUserAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        this.userRepository.findAll().forEach(
                user -> userDtoList.add(new UserDto(user))
        );
        return userDtoList;
    }

    public void updateUser(Long id, UserDto dto) {
        Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);

        //empty
        if (userEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        //set value
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(
                dto.getPassword() == null ? userEntity.getPassword() : dto.getPassword()
        );
        userEntity.setShopOwner(
                dto.getShopOwner() == null ? userEntity.getShopOwner() : dto.getShopOwner()
        );
        Optional<AreaEntity> newArea = this.areaRepository.findById(
                dto.getId() == null ? userEntity.getResidence().getId() : dto.getAreaId()
        );
        newArea.ifPresent(userEntity::setResidence);
        //save
        this.userRepository.save(userEntity);
    }
    public void deleteUser(Long id) {
        if (!this.userRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        this.userRepository.deleteById(id);
    }
}
