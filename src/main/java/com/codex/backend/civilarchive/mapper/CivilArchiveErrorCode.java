package com.codex.backend.civilarchive.mapper;

import com.codex.backend.system.error.ErrorCode;

public enum CivilArchiveErrorCode implements ErrorCode {

        PERSON_NOT_FOUND(
                        "CIVIL_REGISTRY.PERSON.NOT_FOUND"),

        PERSON_GENDER_ROLE_MISMATCH(
                        "CIVIL_REGISTRY.PERSON_GENDER_ROLE_MISMATCH"),

        PERSON_NATIONAL_ID_EXISTS(
                        "CIVIL_REGISTRY.PERSON.NATIONAL_NUMBER_EXISTS"),

        PERSON_PHONE_NUMBER_EXISTS(
                        "CIVIL_REGISTRY.PERSON_PHONE_NUMBER_EXISTS"),

        PERSON_PRIMARY_PHONE_NUMBER_DELETION_NOT_ALLOWED(
                        "CIVIL_REGISTRY.PERSON_PRIMARY_PHONE_NUMBER_DELETION_NOT_ALLOWED"),

        PERSON_PRIMARY_EMAIL_ADDRESS_DELETION_NOT_ALLOWED(
                        "CIVIL_REGISTRY.PERSON_PRIMARY_EMAIL_ADDRESS_DELETION_NOT_ALLOWED"),

        PERSON_PRIMARY_ADDRESS_DELETION_NOT_ALLOWED(
                        "CIVIL_REGISTRY.PERSON_PRIMARY_ADDRESS_DELETION_NOT_ALLOWED"),

        PERSON_PHONE_NUMBER_NOT_FOUND(
                        "CIVIL_REGISTRY.PERSON_PHONE_NUMBER_NOT_FOUND"),

        PERSON_EMAIL_ADDRESS_EXISTS(
                        "CIVIL_REGISTRY.PERSON_EMAIL_ADDRESS_EXISTS"),

        PERSON_INVALID_REGION(
                        "CIVIL_REGISTRY.PERSON.INVALID_REGION"),

        PERSON_INSUFFICIENT_IDENTIFICATION(
                        "CIVIL_REGISTRY.PERSON.INSUFFICIENT_IDENTIFICATION"),

        PERSON_INVALID_BIRTH_DATE(
                        "CIVIL_REGISTRY.PERSON.PERSON_INVALID_BIRTH_DATE");

        private final String code;

        CivilArchiveErrorCode(String code) {
                this.code = code;
        }

        @Override
        public String code() {
                return code;
        }
}
