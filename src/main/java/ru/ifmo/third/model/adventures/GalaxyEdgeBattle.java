package ru.ifmo.third.model.adventures;

import ru.ifmo.third.model.Action;

public class GalaxyEdgeBattle extends Adventure {

    public GalaxyEdgeBattle() {
        super(3);
    }

    @Override
    public Action executeStrategy() {
        System.out.println("Победа в битве на границе Галактики!");
        return Action.SEEK_GLORY;
    }
}


