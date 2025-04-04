package ru.ifmo.third.model.reactions;

import ru.ifmo.third.model.Action;
import ru.ifmo.third.model.alive.LiveEntity;
import ru.ifmo.third.model.triggers.Trigger;

public class ConfusedReaction implements Reaction {
    @Override
    public Action respond(Trigger trigger, LiveEntity entity) {
        System.out.println(entity.getName() + " в замешательстве и игнорирует все");
        return Action.IGNORE;
    }
}
