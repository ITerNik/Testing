package ru.ifmo.third.models.environment;

import lombok.Data;
import ru.ifmo.third.models.creatures.AbstractCreature;
import ru.ifmo.third.models.creatures.Creature;
import ru.ifmo.third.models.creatures.Human;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Planet {
    private String name;
    private Sea sea;
    private double dayLengthHours;
    private Climate climate;
    private List<Human> people = new ArrayList<>();
    private List<Creature> creatures = new ArrayList<>();

    public Planet(String name, Sea sea, double dayLengthHours, Climate climate) {
        this.name = name;
        this.sea = sea;
        this.dayLengthHours = dayLengthHours;
        this.climate = climate;
    }

    private void checkCreature(AbstractCreature creature) {
        if (!creature.isAuthentic()) throw new IllegalArgumentException(String.format("%s должен быть настоящим, чтобы жить на планете", creature.getName()));
    }

    public void addHuman(Human human) {
        checkCreature(human);
        human.setPlanet(this);
        people.add(human);
    }

    public void addCreature(Creature creature) {
        checkCreature(creature);
        creature.setPlanet(this);
        creatures.add(creature);
    }

    public Planet setCreatures(List<Creature> creatures) {
        this.creatures = creatures.stream().peek(creature -> {
            checkCreature(creature);
            creature.setPlanet(this);
        }).collect(Collectors.toList());
        return this;
    }

    public Planet setPeople(List<Human> people) {
        this.people = people.stream().peek(human -> {
            checkCreature(human);
            human.setPlanet(this);
        }).collect(Collectors.toList());
        return this;
    }
}
