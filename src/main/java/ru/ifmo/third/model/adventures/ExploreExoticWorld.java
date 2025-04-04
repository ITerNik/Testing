package ru.ifmo.third.model.adventures;

import ru.ifmo.third.model.Action;

public class ExploreExoticWorld extends Adventure {
    public ExploreExoticWorld() {
        super(4);
    }

    @Override
    public Action executeStrategy() {
        System.out.println("Команда исследует экзотическое солнце...");
        return Action.EXPEDITION_SUCCESS;
    }
}



