package com.seiji.dslist.dto;

import com.seiji.dslist.enums.Role;

public record RegisterDTO(String username, String password, Role role) {
}
