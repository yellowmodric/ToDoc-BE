package com.solinone.todoc.user.domain;

import com.solinone.todoc.global.common.DeletedBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

        @Column
        private String name;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String nickname;

        @Builder(access = AccessLevel.PRIVATE)
        public User(UserRole role,String name, String nickname, String email, String password) {
                this.role = role;
                this.name = name;
                this.nickname = nickname;
                this.email = email;
                this.password = password;
        }

        public static User createVisitor(String nickname, String email, String password) {
                return User.builder()
                        .role(UserRole.VISITOR)
                        .name(null)
                        .nickname(nickname)
                        .email(email)
                        .password(password)
                        .build();
        }

        public static User createProvider(String name, String nickname, String email, String password) {
                return User.builder()
                        .role(UserRole.PROVIDER)
                        .name(name)
                        .nickname(nickname)
                        .email(email)
                        .password(password)
                        .build();
        }
}
