package ru.ifmo.third.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ifmo.third.models.environment.Planet;

@Data
@AllArgsConstructor
public class Adventure {
    private Ship ship;
    private Planet destination;

    public void start() {
        this.ship.move(this.destination);
    }
}
