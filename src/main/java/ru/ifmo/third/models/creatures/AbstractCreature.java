package ru.ifmo.third.models.creatures;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.third.models.environment.Planet;
import ru.ifmo.third.models.words.Word;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractCreature {
    protected String name;
    private Planet planet;
    protected List<Fear> fears = new ArrayList<>();
    private boolean isAuthentic = true;

    public void inclineWord(Word word) throws NoSuchMethodException {
        throw new NoSuchMethodException(String.format("%s не умеет склонять слова", this.name));
    }
}
