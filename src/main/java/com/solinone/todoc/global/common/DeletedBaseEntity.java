package com.solinone.todoc.global.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class DeletedBaseEntity extends BaseEntity{

    protected LocalDateTime deletedAt;
}
