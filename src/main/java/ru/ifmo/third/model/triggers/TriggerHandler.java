package ru.ifmo.third.model.triggers;

import ru.ifmo.third.model.Action;
import ru.ifmo.third.model.Empire;
import ru.ifmo.third.model.alive.LiveEntity;
import ru.ifmo.third.model.reactions.AfraidReaction;
import ru.ifmo.third.model.reactions.BraveReaction;
import ru.ifmo.third.model.reactions.ConfusedReaction;
import ru.ifmo.third.model.reactions.Reaction;

import java.util.Map;

public class TriggerHandler {
    private final Map<Trigger, Reaction> reactionMap;

    public TriggerHandler() {
        reactionMap = Map.of(
                Trigger.HEROIC_ACT, new BraveReaction(),
                Trigger.STRANGE_PLANET, new ConfusedReaction(),
                Trigger.UNKNOWN, new AfraidReaction()
        );
    }

    public void handleTrigger(Trigger trigger, LiveEntity entity) {
        Reaction reaction = reactionMap.getOrDefault(trigger, new ConfusedReaction());
        Action action = reaction.respond(trigger, entity);
        Empire.getInstance().influenceBy(action);
    }
}
