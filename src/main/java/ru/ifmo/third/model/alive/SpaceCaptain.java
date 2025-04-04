package ru.ifmo.third.model.alive;

import ru.ifmo.third.model.adventures.Adventure;

import java.util.logging.Logger;

public class SpaceCaptain extends LiveEntity {
    private static final Logger logger = Logger.getLogger(SpaceCaptain.class.getName());
    private Adventure currentAdventure;

    public SpaceCaptain(String name) {
        super(name);
        this.currentAdventure = null;
    }

    public boolean isOnAdventure() {
        return currentAdventure != null;
    }

    public void beginAdventure(Adventure adventure) {
        if (isOnAdventure()) {
            throw new IllegalStateException(getName() + " уже участвует в приключении!");
        }
        this.currentAdventure = adventure;
        adventure.addParticipant(this);
        logger.info(getName() + " начинает новое приключение");
    }

    public void endAdventure() {
        if (!isOnAdventure()) {
            logger.info(getName() + " не находится в приключении");
            return;
        }
        logger.info(getName() + " завершает приключение");
        this.currentAdventure = null;
    }
}


