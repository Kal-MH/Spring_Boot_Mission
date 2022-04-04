package dev.kalmh.basic.repository;

import dev.kalmh.basic.entity.ShopEntity;
import org.springframework.data.repository.CrudRepository;

public interface ShopRepository extends CrudRepository<ShopEntity, Long> {
}
