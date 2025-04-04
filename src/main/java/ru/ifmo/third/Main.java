package ru.ifmo.third;

import ru.ifmo.third.model.Empire;
import ru.ifmo.third.model.adventures.Adventure;
import ru.ifmo.third.model.adventures.ExploreExoticWorld;
import ru.ifmo.third.model.alive.FurryAlphaCentaurian;
import ru.ifmo.third.model.alive.SpaceCaptain;
import ru.ifmo.third.model.triggers.Trigger;
import ru.ifmo.third.model.triggers.TriggerHandler;

public class Main {
    public static void main(String[] args) {
        SpaceCaptain zaphod = new SpaceCaptain("Zaphod");
        FurryAlphaCentaurian fluffy = new FurryAlphaCentaurian("Fluff");

        TriggerHandler handler = new TriggerHandler();

        handler.handleTrigger(Trigger.HEROIC_ACT, zaphod);
        handler.handleTrigger(Trigger.UNKNOWN, fluffy);

        Adventure sunAdventure = new ExploreExoticWorld();

        zaphod.beginAdventure(sunAdventure);

        Empire empire = Empire.getInstance();
        empire.influenceBy(sunAdventure.completeAdventure());
    }
}

