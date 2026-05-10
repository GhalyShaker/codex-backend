package com.codex.backend.civilarchive.repository.table;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.backend.civilarchive.entity.table.PersonPhoneNumber;

@Repository
public interface PersonPhoneNumberRepository extends JpaRepository<PersonPhoneNumber, Long> {
    Optional<PersonPhoneNumber> findByPersonPersonIdAndPrimaryTrue(Long personId);

    Page<PersonPhoneNumber> findByPersonPersonId(Long personId, Pageable pageable);

    Boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPersonPersonId(Long personId);

    Optional<PersonPhoneNumber> findFirstByPersonPersonIdAndPhoneNumberIdNotOrderByCreatedAtAsc(Long personId, Long phoneNumberId);

    Optional<PersonPhoneNumber> findByPhoneNumber(String phoneNumber);

    Optional<PersonPhoneNumber> findByPhoneNumberIdAndPerson_PersonId(Long phoneNumberId, Long personId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE PersonPhoneNumber p SET p.primary = false WHERE p.person.personId = :personId")
    void clearPrimaryByPersonId(@Param("personId") Long personId);
}
