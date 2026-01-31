package com.solinone.todoc.user.domain;

import com.solinone.todoc.global.common.DeletedBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends DeletedBaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long userId;

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private UserRole role;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String nickname;
}
