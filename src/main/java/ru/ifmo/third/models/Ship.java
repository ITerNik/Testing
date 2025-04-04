package ru.ifmo.third.models;

import lombok.Data;
import ru.ifmo.third.models.creatures.Human;
import ru.ifmo.third.models.environment.Planet;

import java.util.List;

@Data
public class Ship {
    private List<Human> crew;
    private Planet location;

    public Ship(List<Human> crew, Planet location) {
        crew.forEach(person -> {
            if (!person.getPlanet().equals(location)) {
                throw new IllegalArgumentException(String.format("%s находится на планете %s, а корабль на %s", person.getName(), person.getPlanet().getName(), location.getName()));
            }
        });
        this.crew = crew;
        this.location = location;

    }

    public void move(Planet planet) {
        crew.forEach(person -> person.setPlanet(planet));
        this.location = planet;
    }
}
