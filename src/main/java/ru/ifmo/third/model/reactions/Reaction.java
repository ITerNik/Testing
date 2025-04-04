package ru.ifmo.third.model.reactions;

import ru.ifmo.third.model.Action;
import ru.ifmo.third.model.alive.LiveEntity;
import ru.ifmo.third.model.triggers.Trigger;

public interface Reaction {
    Action respond(Trigger trigger, LiveEntity entity);
}

