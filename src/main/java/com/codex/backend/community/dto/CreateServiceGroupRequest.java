package com.codex.backend.community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateServiceGroupRequest {
    @NotBlank(message = "groupName is required")
    String groupName;

    @NotNull(message = "groupSupervisorId is required")
    Integer groupSupervisorId;

    String description;

    Boolean createAccount;

    Integer accountSupervisorId;
}
