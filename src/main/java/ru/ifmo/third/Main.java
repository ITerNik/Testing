package ru.ifmo.third;

import ru.ifmo.third.models.Adventure;
import ru.ifmo.third.models.Galaxy;
import ru.ifmo.third.models.Ship;
import ru.ifmo.third.models.creatures.Gender;
import ru.ifmo.third.models.creatures.Human;
import ru.ifmo.third.models.environment.Climate;
import ru.ifmo.third.models.environment.Planet;
import ru.ifmo.third.models.environment.Sea;
import ru.ifmo.third.models.environment.Shade;
import ru.ifmo.third.models.words.Word;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Human test = new Human("Тест", Gender.MALE, false, true, true);
        Planet planet = new Planet("Тест", new Sea("Тест", Shade.AQUA), 12, Climate.ARCTIC).setPeople(List.of(test));
        Galaxy galaxy = new Galaxy(
                List.of(planet),
                List.of(new Word("склон")),
                List.of(new Adventure(new Ship(List.of(test), planet), new Planet("Тест 2", new Sea("Тест 2", Shade.BLUE), 12, Climate.MEDITERRANEAN)))
        );
    }
}
