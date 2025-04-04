package ru.ifmo.third.model.adventures;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.third.model.Action;
import ru.ifmo.third.model.alive.SpaceCaptain;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
public abstract class Adventure {
    private static final Logger logger = Logger.getLogger(Adventure.class.getName());

    @Getter
    private final int requiredParticipants;
    @Getter
    private final List<SpaceCaptain> participants = new ArrayList<>();
    private boolean isCompleted = false;

    public void addParticipant(SpaceCaptain captain) {
        if (!isCompleted) {
            participants.add(captain);
            logger.info(captain.getName() + " вступил в приключение!");
        } else {
            logger.info("Приключение уже завершено, " + captain.getName() + " не может вступить.");
        }
    }

    public abstract Action executeStrategy();

    public Action completeAdventure() {
        isCompleted = true;
        participants.forEach(SpaceCaptain::endAdventure);
        if (participants.size() >= requiredParticipants) {
            return executeStrategy();
        } else {
            logger.warning("Приключение завершилось провалом");
            return Action.FAILURE;
        }
    }
}


