package ru.ifmo.third.model;

import lombok.Getter;

public class Empire {
    private static Empire instance;
    @Getter
    private int glory;
    @Getter
    private int stability;

    private Empire() {
        this.glory = 100;
        this.stability = 100;
    }

    public static Empire getInstance() {
        if (instance == null) {
            instance = new Empire();
        }
        return instance;
    }

    public void influenceBy(Action action) {
        this.glory += action.getGlory();
        this.stability += action.getStability();
    }
}


