package ru.ifmo.third.models.words;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NonInclineWord extends Word {
    public NonInclineWord(String text) {
        super(text);
    }

    @Override
    public void incline() {
        throw new IllegalArgumentException("Слово несклоняемое");
    }
}
