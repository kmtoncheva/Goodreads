package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class TextTokenizerTest {
    String stopwordsData = "which\n" +
        "while\n" +
        "was\n" +
        "who";
    TextTokenizer tokenizer = new TextTokenizer(new StringReader(stopwordsData));

    @Test
    void testStopwordsLoading() {
        Set<String> expectedStopwords = Set.of("which", "while", "was", "who");
        assertEquals(expectedStopwords, tokenizer.stopwords(), "Stopwords should be loaded correctly.");
    }

    @Test
    void testTokenizeWithBasicInput() {
        String input = "While he was sleeping, who let the dog out?";
        List<String> expected = List.of("he", "sleeping", "let", "the", "dog", "out");
        assertIterableEquals(expected, tokenizer.tokenize(input), "Should tokenize and remove stopwords correctly.");
    }

    @Test
    void testTokenizeWithEmptyInput() {
        String input = "";
        List<String> expected = List.of();
        assertEquals(expected, tokenizer.tokenize(input), "Should return an empty list for empty input.");
    }

    @Test
    void testTokenizeWithNullInput() {
        List<String> expected = List.of();
        assertEquals(expected, tokenizer.tokenize(null), "Should return an empty list for null input.");
    }

    @Test
    void testTokenizeWithPunctuationAndSpaces() {
        String input = "Hello,      world?!! User-friendly ... test.";
        List<String> expected = List.of("hello", "world", "userfriendly", "test");
        assertIterableEquals(expected, tokenizer.tokenize(input), "Should handle punctuation and extra spaces.");
    }

    @Test
    void testTokenizeWithAllStopwords() {
        String input = stopwordsData;
        List<String> expected = List.of();
        assertEquals(expected, tokenizer.tokenize(input), "Should return an empty list when all tokens are stopwords.");
    }

    @Test
    void testTokenizeWithNoStopwords() {
        String input = "JAVA is ...... fun.";
        List<String> expected = List.of("java", "is", "fun");
        assertEquals(expected, tokenizer.tokenize(input),
            "Should return tokens correctly when no stopwords are present.");
    }
}