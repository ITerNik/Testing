package ru.ifmo.third.models.creatures;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.ifmo.third.models.environment.Climate;
import ru.ifmo.third.models.environment.Planet;
import ru.ifmo.third.models.environment.Shade;
import ru.ifmo.third.models.words.PartOfSpeech;
import ru.ifmo.third.models.words.Word;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Human extends AbstractCreature {
    private Preference preference = new Preference(
            List.of(Climate.values()),
            List.of(Shade.values()),
                0,
            Double.POSITIVE_INFINITY
    );
    private Planet planet;
    private Gender gender;
    private boolean isPoor;
    private boolean isWorthyOfMention;
    private boolean isBraveHeart = false;

    public Human(Preference preference, String name, Gender gender, boolean isPoor, boolean isWorthyOfMention, boolean isBraveHeart) {
        checkPoorMentioned(isPoor, isWorthyOfMention);

        this.name = name;
        this.preference = preference;
        this.gender = gender;
        this.isPoor = isPoor;
        this.isWorthyOfMention = isWorthyOfMention;
        this.isBraveHeart = isBraveHeart;
    }

    public Human(String name, Gender gender, boolean isPoor, boolean isWorthyOfMention, boolean isBraveHeart) {
        checkPoorMentioned(isPoor, isWorthyOfMention);

        this.name = name;
        this.gender = gender;
        this.isPoor = isPoor;
        this.isWorthyOfMention = isWorthyOfMention;
        this.isBraveHeart = isBraveHeart;
    }

    private void checkPoorMentioned(boolean isPoor, boolean isWorthyOfMention) {
        if (isPoor && isWorthyOfMention) throw new IllegalArgumentException("Достойный упоминания, но бедный");
    }

    private void checkSatisfaction(boolean isSatisfied, boolean isPoor) {
        if (!isSatisfied && isPoor) throw new IllegalArgumentException("Бедный, но недовольный условиями");
    }

    public void setPlanet(Planet planet) {
        boolean iSatisfied = this.preference.check(planet);
        checkSatisfaction(iSatisfied, isPoor);
        this.planet = planet;
    }

    public void setPreference(Preference preference) {
        boolean iSatisfied = preference.check(this.planet);
        checkSatisfaction(iSatisfied, isPoor);
        this.preference = preference;
    }

    public void setPoor(boolean poor) {
        checkPoorMentioned(poor, this.isWorthyOfMention);
        this.isPoor = poor;
    }

    public void setWorthyOfMention(boolean worthyOfMention) {
        checkPoorMentioned(this.isPoor, worthyOfMention);
        this.isWorthyOfMention = worthyOfMention;
    }

    public void doBraveAction() throws NoSuchMethodException {
        if (!this.isBraveHeart || this.fears.contains(Fear.BRAVE_ACTION)) {
            throw new NoSuchMethodException(String.format("%s боится геройствовать", this.name));
        }
    }

    public void meetUnknown() {
        if (fears.contains(Fear.UNKNOWN)) throw new IllegalArgumentException(String.format("%s боится неизвестности", this.name));
        System.out.printf("%s бросает вызов неизвестности%n", this.name);
    }

    @Override
    public void inclineWord(Word word) {
        try {
            word.incline();
            System.out.printf("%s склоняет слово \"%s\"%n", this.name, word.getText());
        } catch (IllegalArgumentException e) {
            if (!word.getPartOfSpeech().equals(PartOfSpeech.NOUN)) throw new IllegalArgumentException(String.format("%s может склонять только несклоняемые существительные, но не \"%s\"", this.name, word.getText()));
            if (this.fears.contains(Fear.INCLINE)) throw new IllegalArgumentException(String.format("%s боится склонять несклоняемое \"%s\"", this.name, word.getText()));
            System.out.printf("%s склоняет несклоняемое существительное \"%s\"%n", this.name, word.getText());
        }
    }
}
