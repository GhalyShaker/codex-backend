package com.codex.backend.system.entity;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.codex.backend.system.entity.enums.IdempotencyStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "idempotency_keys", schema = "system")
public class IdempotencyKey {

    @Id
    private UUID idempotencyKey;

    private String operation;
    private String requestHash;

    @Enumerated(EnumType.STRING)
    private IdempotencyStatus status;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String result;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private OffsetDateTime expiresAt;

    public static IdempotencyKey create(UUID key, String operation, String requestHash, Duration ttl) {
        OffsetDateTime now = OffsetDateTime.now();

        IdempotencyKey e = new IdempotencyKey();
        e.idempotencyKey = key;
        e.operation = operation;
        e.requestHash = requestHash;
        e.status = IdempotencyStatus.PROCESSING;
        e.createdAt = now;
        e.updatedAt = now;
        e.expiresAt = now.plus(ttl);
        return e;
    }

    public void markComplete(String result) {
        this.result = result;
        this.status = IdempotencyStatus.COMPLETED;
        this.updatedAt = OffsetDateTime.now();
    }

    public boolean isCompleted() {
        return status == IdempotencyStatus.COMPLETED;
    }

    public boolean matchesRequest(String op, String hash) {
        return operation.equals(op) && requestHash.equals(hash);
    }
}
