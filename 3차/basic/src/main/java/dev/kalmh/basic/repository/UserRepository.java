package dev.kalmh.basic.repository;

import dev.kalmh.basic.entity.BoardEntity;
import dev.kalmh.basic.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
