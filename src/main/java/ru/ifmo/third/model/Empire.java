package ru.ifmo.third.model;

import lombok.Getter;

@Getter
public class Empire {
    private static Empire instance;
    private int glory;
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

    public static void reset() {
        instance = null;
    }

    public void influenceBy(Action action) {
        this.glory += action.getGlory();
        this.stability += action.getStability();
    }
}


