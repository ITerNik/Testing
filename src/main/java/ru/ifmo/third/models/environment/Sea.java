package ru.ifmo.third.models.environment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sea {
    private String name;
    private Shade shade;
}