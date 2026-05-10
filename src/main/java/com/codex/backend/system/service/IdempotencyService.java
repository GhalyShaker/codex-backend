package com.codex.backend.system.service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codex.backend.system.entity.IdempotencyKey;
import com.codex.backend.system.error.SystemErrorCode;
import com.codex.backend.system.exception.ConflictException;
import com.codex.backend.system.repository.IdempotencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final IdempotencyRepository repository;
    private final ObjectMapper objectMapper;
    private final RequestHashService requestHashService;

    @Transactional
    public <T> T execute(
            UUID key,
            String operation,
            Object request,
            Duration ttl,
            Class<T> responseType,
            Supplier<T> action) {

        String hash = requestHashService.hash(request);

        /*
         * Attempt to claim this idempotency key.
         *
         * inserted = 1:
         *   This transaction created the key and owns execution.
         *
         * inserted = 0:
         *   Another transaction already owns this key.
         *
         * Note:
         * PostgreSQL may block here until the other transaction commits,
         * meaning the record may already be COMPLETED when we continue.
         */
        int inserted = repository.tryInsert(
                key,
                operation,
                hash,
                OffsetDateTime.now().plus(ttl));


        IdempotencyKey record = repository.findByKey(key)
                .orElseThrow(() ->
                        new IllegalStateException("Idempotency record missing"));


        /*
         * The same idempotency key cannot be reused for a different request.
         */
        if (!record.matchesRequest(operation, hash)) {
            throw new ConflictException(
                    SystemErrorCode.INVALID_PARAMETER,
                    "Idempotency key reused with different request");
        }


        /*
         * Another request already completed this operation.
         *
         * Return the previously stored result instead of executing again.
         */
        if (record.isCompleted()) {
            return deserialize(record.getResult(), responseType);
        }


        /*
         * Another transaction owns this key and is still processing.
         *
         * Under PostgreSQL concurrency behavior, this case is only reached
         * when the owning transaction has not completed yet.
         */
        if (inserted == 0) {
            throw new ConflictException(
                    SystemErrorCode.INVALID_PARAMETER,
                    "Request already processing");
        }


        /*
         * This transaction successfully claimed the idempotency key.
         * Execute the operation exactly once.
         */
        try {
            T result = action.get();

            String json = serialize(result);

            record.markComplete(json);
            repository.save(record);

            return result;

        } catch (Exception ex) {
            throw ex;
        }
    }


    private String serialize(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private <T> T deserialize(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
