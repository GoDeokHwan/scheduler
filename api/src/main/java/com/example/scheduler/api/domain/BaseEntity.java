package com.example.scheduler.api.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Column
    @CreatedDate
    protected LocalDateTime createDate;

    @Column
    @LastModifiedDate
    protected LocalDateTime modifyDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        createDate = now;
        modifyDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifyDate = LocalDateTime.now().withNano(0);
    }
}
