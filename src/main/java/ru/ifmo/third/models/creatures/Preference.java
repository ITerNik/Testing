package ru.ifmo.third.models.creatures;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ifmo.third.models.environment.Climate;
import ru.ifmo.third.models.environment.Planet;
import ru.ifmo.third.models.environment.Shade;

import java.util.List;

@Data
@AllArgsConstructor
public class Preference {
    private List<Climate> preferredClimate;
    private List<Shade> preferredShade;
    private double minPreferredDayLength;
    private double maxPreferredDayLength;

    public boolean check(Planet planet) {
        boolean res = this.preferredClimate.contains(planet.getClimate());
        res = res && this.preferredShade.contains(planet.getSea().getShade());
        double dayLength = planet.getDayLengthHours();
        res = res && this.minPreferredDayLength <= dayLength && this.maxPreferredDayLength >= dayLength;
        return res;
    }
}
