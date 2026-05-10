package com.codex.backend.civilarchive.repository.table;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codex.backend.civilarchive.entity.table.PersonAddress;

@Repository
public interface PersonAddressRepository
                extends JpaRepository<PersonAddress, Long> {

        Optional<PersonAddress> findByPersonPersonIdAndPrimaryTrue(Long personId);

        Page<PersonAddress> findByPersonPersonId(Long personId, Pageable pageable);

        boolean existsByPersonPersonId(Long personId);

        Optional<PersonAddress> findFirstByPersonPersonIdAndAddressIdNotOrderByCreatedAtAsc(Long personId, Long addressId);

        Optional<PersonAddress> findByAddressIdAndPerson_PersonId(Long addressId, Long personId);

        @Modifying(clearAutomatically = true, flushAutomatically = true)
        @Query("""
                        UPDATE PersonAddress a
                        SET a.primary = false
                        WHERE a.person.personId = :personId
                        """)
        void clearPrimaryByPersonId(Long personId);
}
