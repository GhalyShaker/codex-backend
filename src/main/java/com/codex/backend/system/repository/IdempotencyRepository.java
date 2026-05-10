package com.codex.backend.system.repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.backend.system.entity.IdempotencyKey;

@Repository
public interface IdempotencyRepository extends JpaRepository<IdempotencyKey, UUID> {

    @Modifying
    @Query(value = """
        INSERT INTO system.idempotency_keys (
            idempotency_key,
            operation,
            request_hash,
            status,
            result,
            created_at,
            updated_at,
            expires_at
        )
        VALUES (
            :key,
            :operation,
            :requestHash,
            'PROCESSING',
            NULL,
            now(),
            now(),
            :expiresAt
        )
        ON CONFLICT (idempotency_key)
        DO NOTHING
        """, nativeQuery = true)
    int tryInsert(
            @Param("key") UUID key,
            @Param("operation") String operation,
            @Param("requestHash") String requestHash,
            @Param("expiresAt") OffsetDateTime expiresAt
    );

    @Query("""
        SELECT i FROM IdempotencyKey i
        WHERE i.idempotencyKey = :key
    """)
    Optional<IdempotencyKey> findByKey(@Param("key") UUID key);
}
