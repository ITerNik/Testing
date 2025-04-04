package third.models;

import config.TimingRules;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ifmo.third.models.words.NonInclineWord;
import ru.ifmo.third.models.words.PartOfSpeech;
import ru.ifmo.third.models.words.Word;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TimingRules.class)
class WordTests {
    @Test
    @DisplayName("Определение глаголов")
    void testVerbDetection() {
        assertEquals(PartOfSpeech.VERB, new Word("бегать").getPartOfSpeech());
        assertEquals(PartOfSpeech.VERB, new Word("плыть").getPartOfSpeech());
        assertEquals(PartOfSpeech.VERB, new Word("обнять").getPartOfSpeech());
        assertEquals(PartOfSpeech.VERB, new Word("крикнуть").getPartOfSpeech());
    }

    @Test
    @DisplayName("Определение прилагательных")
    void testAdjectiveDetection() {
        assertEquals(PartOfSpeech.ADJECTIVE, new Word("синий").getPartOfSpeech());
        assertEquals(PartOfSpeech.ADJECTIVE, new Word("большой").getPartOfSpeech());
        assertEquals(PartOfSpeech.ADJECTIVE, new Word("красивая").getPartOfSpeech());
        assertEquals(PartOfSpeech.ADJECTIVE, new Word("глупое").getPartOfSpeech());
        assertEquals(PartOfSpeech.ADJECTIVE, new Word("необычное").getPartOfSpeech());
    }

    @Test
    @DisplayName("Определение существительных")
    void testNounDetection() {
        assertEquals(PartOfSpeech.NOUN, new Word("дерево").getPartOfSpeech());
        assertEquals(PartOfSpeech.NOUN, new Word("кот").getPartOfSpeech());
        assertEquals(PartOfSpeech.NOUN, new Word("человек").getPartOfSpeech());
        assertEquals(PartOfSpeech.NOUN, new Word("склон").getPartOfSpeech());
        assertEquals(PartOfSpeech.NOUN, new Word("река").getPartOfSpeech());
    }

    @Test
    @DisplayName("Склонение слова")
    void testWordIncline() {
        Word word = new Word("гора");
        assertDoesNotThrow(word::incline);
    }

    @Test
    @DisplayName("Создание несклоняемого слова")
    void testNonInclineWordCreation() {
        NonInclineWord word = new NonInclineWord("кафе");
        assertEquals("кафе", word.getText());
    }

    @Test
    @DisplayName("Попытка склонения несклоняемого слова вызывает исключение")
    void testNonInclineWordThrowsException() {
        NonInclineWord word = new NonInclineWord("кино");
        Exception exception = assertThrows(IllegalArgumentException.class, word::incline);
        assertEquals("Слово несклоняемое", exception.getMessage());
    }
}
