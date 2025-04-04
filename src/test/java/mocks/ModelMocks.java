package mocks;

import ru.ifmo.third.models.creatures.Gender;
import ru.ifmo.third.models.creatures.Human;
import ru.ifmo.third.models.environment.Climate;
import ru.ifmo.third.models.environment.Planet;
import ru.ifmo.third.models.environment.Sea;
import ru.ifmo.third.models.environment.Shade;

import java.util.Collections;
import java.util.List;

public class ModelMocks {
    public static Sea getSeaMock() {
        return new Sea("Sea", Shade.AQUA);
    }
    public static Human getHumanMock() {
        return new Human("Human", Gender.MALE, false, true, true);
    }
    public static Planet getInhabitPlanetMock() {
        Planet planet = new Planet("Planet", getSeaMock(), 12, Climate.MEDITERRANEAN);
        List<Human> people = Collections.nCopies(4, getHumanMock());
        planet.setPeople(people);
        return planet;
    }
}
