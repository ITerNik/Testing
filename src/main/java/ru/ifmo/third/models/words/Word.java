package ru.ifmo.third.models.words;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Word {
    private String text;
    private PartOfSpeech partOfSpeech;

    public Word(String text) {
        this.text = text;
        this.partOfSpeech = determinePartOfSpeech(text);
    }

    private PartOfSpeech determinePartOfSpeech(String word) {
        word = word.toLowerCase();

        if (word.matches(".*(ать|ить|ыть|ять|нуть)$")) {
            return PartOfSpeech.VERB;
        } else if (word.matches(".*(ый|ий|ой|ая|ое|ее)$")) {
            return PartOfSpeech.ADJECTIVE;
        } else {
            return PartOfSpeech.NOUN;
        }
    }

    public void incline() {
        System.out.printf("Слово \"%s\" склонено%n", this.text);
    }
}
