package ru.ifmo.third.model.alive;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class LiveEntity {
    private final String name;
}
