package com.seiji.dslist.game.application.dto;


import lombok.Getter;

public record ReplacementDTO(
        Integer fromIndex,
        Integer toIndex
) {
    public ReplacementDTO {
        if (fromIndex == null || toIndex == null) {
            throw new IllegalArgumentException("Indices cannot be null");
        }
        if (fromIndex < 0 || toIndex < 0) {
            throw new IllegalArgumentException("Indices must be non-negative");
        }
    }
    public int getFromIndex() {
        return fromIndex;
    }
    public int getToIndex() {
        return toIndex;
    }
}