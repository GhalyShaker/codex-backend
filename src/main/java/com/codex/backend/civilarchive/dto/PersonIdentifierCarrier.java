package com.codex.backend.civilarchive.dto;

public interface PersonIdentifierCarrier {
    String nationalNumber();

    String phoneNumber();

    Long fatherId();

    Long motherId();
}
