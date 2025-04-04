package ru.ifmo.third.model.reactions;

import ru.ifmo.third.model.Action;
import ru.ifmo.third.model.alive.LiveEntity;
import ru.ifmo.third.model.triggers.Trigger;

public class AfraidReaction implements Reaction {
    @Override
    public Action respond(Trigger trigger, LiveEntity entity) {
        System.out.println(entity.getName() + " в панике убегает!");
        return Action.PANIC;
    }
}
