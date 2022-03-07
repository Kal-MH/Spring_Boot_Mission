package dev.kalmh.basic.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private Instant createAt;

    @LastModifiedDate
    @Column(updatable = true)
    private Instant updatedAt;

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
