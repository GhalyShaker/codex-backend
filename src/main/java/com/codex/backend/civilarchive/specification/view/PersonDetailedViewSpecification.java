package com.codex.backend.civilarchive.specification.view;

import com.codex.backend.civilarchive.dto.filter.PersonFilter;
import com.codex.backend.civilarchive.entity.view.PersonDetailedView;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class PersonDetailedViewSpecification {

    private PersonDetailedViewSpecification() {
    }

    public static Specification<PersonDetailedView> filter(PersonFilter filter) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // IDs
            addEqual(predicates, cb, root.get("personId"), filter.getPersonId());
            addEqual(predicates, cb, root.get("fatherId"), filter.getFatherId());
            addEqual(predicates, cb, root.get("motherId"), filter.getMotherId());

            // Enum
            addEqual(predicates, cb, root.get("gender"), filter.getGender());

            // Exact match fields
            addEqual(predicates, cb, root.get("nationalNumber"), filter.getNationalNumber());
            addEqual(predicates, cb, root.get("bloodType"), filter.getBloodType());

            // ===== PERSON NAMES (PREFIX SEARCH) =====

            addStartsWith(predicates, cb, root.get("firstNameArabic"), filter.getFirstNameArabic());
            addStartsWith(predicates, cb, root.get("fatherNameArabic"), filter.getFatherNameArabic());
            addStartsWith(predicates, cb, root.get("grandfatherNameArabic"), filter.getGrandfatherNameArabic());
            addStartsWith(predicates, cb, root.get("greatGrandfatherNameArabic"),
                    filter.getGreatGrandfatherNameArabic());

            addStartsWith(predicates, cb, root.get("firstNameEnglish"), filter.getFirstNameEnglish());
            addStartsWith(predicates, cb, root.get("fatherNameEnglish"), filter.getFatherNameEnglish());
            addStartsWith(predicates, cb, root.get("grandfatherNameEnglish"), filter.getGrandfatherNameEnglish());
            addStartsWith(predicates, cb, root.get("greatGrandfatherNameEnglish"),
                    filter.getGreatGrandfatherNameEnglish());

            // ===== MOTHER NAMES (PREFIX SEARCH) =====

            addStartsWith(predicates, cb, root.get("motherFirstNameArabic"), filter.getMotherFirstNameArabic());
            addStartsWith(predicates, cb, root.get("motherFatherNameArabic"), filter.getMotherFatherNameArabic());
            addStartsWith(predicates, cb, root.get("motherGrandfatherNameArabic"),
                    filter.getMotherGrandfatherNameArabic());

            addStartsWith(predicates, cb, root.get("motherFirstNameEnglish"), filter.getMotherFirstNameEnglish());
            addStartsWith(predicates, cb, root.get("motherFatherNameEnglish"), filter.getMotherFatherNameEnglish());
            addStartsWith(predicates, cb, root.get("motherGrandfatherNameEnglish"),
                    filter.getMotherGrandfatherNameEnglish());

            // Contacts (still prefix search)
            addStartsWith(predicates, cb, root.get("personPrimaryPhoneNumber"), filter.getPersonPrimaryPhoneNumber());
            addStartsWith(predicates, cb, root.get("fatherPrimaryPhoneNumber"), filter.getFatherPrimaryPhoneNumber());
            addStartsWith(predicates, cb, root.get("motherPrimaryPhoneNumber"), filter.getMotherPrimaryPhoneNumber());

            addStartsWith(predicates, cb, root.get("personPrimaryEmailAddress"), filter.getPersonPrimaryEmailAddress());

            // Dates

            if (filter.getBirthDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("birthDate"), filter.getBirthDateFrom()));
            }

            if (filter.getBirthDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("birthDate"), filter.getBirthDateTo()));
            }

            if (filter.getCreatedAtFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtFrom()));
            }

            if (filter.getCreatedAtTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtTo()));
            }

            if (filter.getUpdatedAtFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("updatedAt"), filter.getUpdatedAtFrom()));
            }

            if (filter.getUpdatedAtTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("updatedAt"), filter.getUpdatedAtTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    // =========================
    // Helpers
    // =========================

    private static <T> void addEqual(
            List<Predicate> predicates,
            CriteriaBuilder cb,
            jakarta.persistence.criteria.Path<T> path,
            T value) {
        if (value != null) {
            predicates.add(cb.equal(path, value));
        }
    }

    private static void addStartsWith(
            List<Predicate> predicates,
            CriteriaBuilder cb,
            jakarta.persistence.criteria.Path<String> path,
            String value) {
        if (value != null && !value.isBlank()) {

            String normalized = value.trim().toLowerCase() + "%";

            predicates.add(
                    cb.like(
                            cb.lower(path),
                            normalized));
        }
    }
}
