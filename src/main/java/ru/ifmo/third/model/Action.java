package ru.ifmo.third.model;

import lombok.Getter;

@Getter
public enum Action {
    SEEK_GLORY(30, 10),
    EXPEDITION_SUCCESS(10,15),
    IGNORE(-10, 0),
    PANIC(0, -20),
    FAILURE(-10, -10);

    Action(int glory, int stability) {
        this.glory = glory;
        this.stability = stability;
    }

    private final int glory;
    private final int stability;
}

