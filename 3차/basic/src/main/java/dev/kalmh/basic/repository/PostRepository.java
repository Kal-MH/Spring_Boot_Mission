package dev.kalmh.basic.repository;

import dev.kalmh.basic.entity.BoardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<BoardEntity, Long> {
}
