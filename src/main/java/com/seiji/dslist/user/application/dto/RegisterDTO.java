package com.seiji.dslist.user.application.dto;

import com.seiji.dslist.user.enums.Role;

public record RegisterDTO(String username, String password, Role role) {
} 