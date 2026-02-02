package com.solinone.todoc.user.infrastructure;

import com.solinone.todoc.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmailAndDeletedAtIsNull(String email);
}
