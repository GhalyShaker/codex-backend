package com.codex.backend.civilarchive.repository.table;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codex.backend.civilarchive.entity.table.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByNationalNumber(String nationalNumber);

    @EntityGraph(attributePaths = {
            "phoneNumbers",
            "emailAddresses",
            "addresses",
            "father",
            "mother"
    })
    Optional<Person> findByPersonId(Long id);

    Boolean existsByNationalNumber(String nationalNumber);

    // List<Person> findByLastName(String lastName);

    // List<Person> findByFirstNameAndLastName(String firstName, String lastName);

    // List<Person> findByEmailAddress(String emailAddress);

    // List<Person> findByPhoneNumber(String phoneNumber);
}
