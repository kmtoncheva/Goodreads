package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TFIDFSimilarityCalculatorTest {
    Book book = Mockito.mock();
    TextTokenizer textTokenizer = Mockito.mock();
    Set<Book> books = Set.of(book);

    TFIDFSimilarityCalculator tfidfSimilarityCalculator = new TFIDFSimilarityCalculator(books, textTokenizer);

    @Test
    void testComputeTFWithShortDescription() {
        when(book.description()).thenReturn("academy superhero club superhero");
        String input = "academy superhero club superhero";
        when(textTokenizer.tokenize(input)).thenReturn(List.of("academy", "superhero", "club", "superhero"));

        Map<String, Double> expected = Map.of(
            "academy", 0.25,
            "superhero", 0.5,
            "club", 0.25
        );

        assertEquals(expected, tfidfSimilarityCalculator.computeTF(book));
    }

    @Test
    void testComputeTFWithOneWord() {
        when(book.description()).thenReturn("academy");
        String input = "academy";
        when(textTokenizer.tokenize(input)).thenReturn(List.of("academy"));

        Map<String, Double> expected = Map.of(
            "academy", 1.00
        );

        assertEquals(expected, tfidfSimilarityCalculator.computeTF(book));
    }

    @Test
    void testComputeTFWithEmptyDescription() {
        when(book.description()).thenReturn("");
        String input = "";
        when(textTokenizer.tokenize(input)).thenReturn(List.of());

        Map<String, Double> expected = Map.of();

        assertEquals(expected, tfidfSimilarityCalculator.computeTF(book));
    }
}
