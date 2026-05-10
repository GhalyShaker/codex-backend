package com.codex.backend.civilarchive.repository.table;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codex.backend.civilarchive.entity.table.PersonEmailAddress;

@Repository
public interface PersonEmailAddressRepository
                extends JpaRepository<PersonEmailAddress, Long> {

        Optional<PersonEmailAddress> findByPersonPersonIdAndPrimaryTrue(Long personId);

            Optional<PersonEmailAddress> findFirstByPersonPersonIdAndEmailAddressIdNotOrderByCreatedAtAsc(Long personId, Long emailAddressId);

        Page<PersonEmailAddress> findByPersonPersonId(Long personId, Pageable pageable);

        Boolean existsByEmailAddress(String emailAddress);

        boolean existsByPersonPersonId(Long personId);

        Optional<PersonEmailAddress> findByEmailAddress(String emailAddress);

        Optional<PersonEmailAddress> findByEmailAddressIdAndPerson_PersonId(Long emailAddressId, Long personId);

        @Modifying(clearAutomatically = true, flushAutomatically = true)
        @Query("""
                        UPDATE PersonEmailAddress e
                        SET e.primary = false
                        WHERE e.person.personId = :personId
                        """)
        void clearPrimaryByPersonId(Long personId);
}
