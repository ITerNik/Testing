package ru.ifmo.third.models;

import lombok.Data;
import ru.ifmo.third.models.environment.Planet;
import ru.ifmo.third.models.words.Word;

import java.util.List;

@Data
public class Galaxy {
    private List<Planet> planets;
    private List<Word> words;
    private List<Adventure> adventures;

    public Galaxy(List<Planet> planets, List<Word> words, List<Adventure> adventures) {
        this.planets = planets;
        this.words = words;
        this.adventures = adventures;
        initialize();
    }

    private void initialize() {
        this.planets.forEach(planet -> {
            planet.getPeople().forEach(person -> {
                this.words.forEach(person::inclineWord);
            });
        });

        this.adventures.forEach(Adventure::start);
    }
}
