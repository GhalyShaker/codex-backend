package com.codex.backend.identity.repository.table;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codex.backend.identity.dto.UserLoginDTO;
import com.codex.backend.identity.entity.table.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("""
                SELECT new com.codex.backend.identity.dto.UserLoginDTO(
                    u.id,
                    u.email,
                    u.username,
                    u.passwordHash
                )
                FROM User u
                WHERE u.username = :username
            """)
    Optional<UserLoginDTO> findLoginDataByUsername(String username);

    @Query("""
                SELECT new com.codex.backend.identity.dto.UserLoginDTO(
                    u.id,
                    u.email,
                    u.username,
                    u.passwordHash
                )
                FROM User u
                WHERE u.email = :email
            """)
    Optional<UserLoginDTO> findLoginDataByEmail(String email);
}
