package ru.ifmo.third.models.creatures;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.ifmo.third.models.environment.Planet;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Creature extends AbstractCreature {
    public Creature(String name, Planet planet, List<Fear> fears, boolean isAuthentic) {
        super(name, planet, fears, isAuthentic);

        if (!planet.getName().equals("Альфа Центавра")) {
            throw new IllegalArgumentException("Мохнатые существа живут только на Альфа Центавре");
        }
    }
}
