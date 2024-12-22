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
    Book book1 = Mockito.mock();
    Book book2 = Mockito.mock();
    Book book3 = Mockito.mock();
    TextTokenizer textTokenizer = Mockito.mock();
    Set<Book> books = Set.of(book1, book2, book3);

    TFIDFSimilarityCalculator tfidfSimilarityCalculator = new TFIDFSimilarityCalculator(books, textTokenizer);

    @Test
    void testComputeTFWithShortDescription() {
        when(book1.description()).thenReturn("academy superhero club superhero");
        String input = "academy superhero club superhero";
        when(textTokenizer.tokenize(input)).thenReturn(List.of("academy", "superhero", "club", "superhero"));

        Map<String, Double> expected = Map.of(
            "academy", 0.25,
            "superhero", 0.5,
            "club", 0.25
        );

        assertEquals(expected, tfidfSimilarityCalculator.computeTF(book1), "It should return valid weights for each word.");
    }

    @Test
    void testComputeTFWithOneWord() {
        when(book1.description()).thenReturn("academy");
        String input = "academy";
        when(textTokenizer.tokenize(input)).thenReturn(List.of("academy"));

        Map<String, Double> expected = Map.of(
            "academy", 1.00
        );

        assertEquals(expected, tfidfSimilarityCalculator.computeTF(book1), "It should return 1.0 if there is only one word.");
    }

    @Test
    void testComputeTFWithEmptyDescription() {
        when(book1.description()).thenReturn("");
        String input = "";
        when(textTokenizer.tokenize(input)).thenReturn(List.of());

        Map<String, Double> expected = Map.of();

        assertEquals(expected, tfidfSimilarityCalculator.computeTF(book1), "It should return empty map if there are no words.");
    }

    @Test
    void testComputeIDFWithValidExample() {
        when(book1.description()).thenReturn("academy superhero club superhero");
        when(book2.description()).thenReturn("superhero mission save club");
        when(book3.description()).thenReturn("crime murder mystery club");

        String input1 = "academy superhero club superhero";
        when(textTokenizer.tokenize(input1)).thenReturn(List.of("academy", "superhero", "club"));

        String input2 = "superhero mission save club";
        when(textTokenizer.tokenize(input2)).thenReturn(List.of("superhero", "mission", "save", "club"));

        String input3 = "crime murder mystery club";
        when(textTokenizer.tokenize(input3)).thenReturn(List.of("crime", "murder", "mystery", "club"));

        Map<String, Double> expected = Map.of(
            "academy", Math.log10((double) 3),
            "superhero", Math.log10((double) 3 / 2),
            "club", Math.log10((double) 3 / 3)
        );

        assertEquals(expected, tfidfSimilarityCalculator.computeIDF(book1), "It should calculate valid weights for each word.");
    }

    @Test
    void testComputeIDFWithInvalidExample() {
        when(book1.description()).thenReturn("");
        when(book2.description()).thenReturn("");
        when(book3.description()).thenReturn("");

        Book outsideBook = Mockito.mock();
        when(outsideBook.description()).thenReturn("academy");

        String input1 = "";
        when(textTokenizer.tokenize(input1)).thenReturn(List.of());

        String input2 = "";
        when(textTokenizer.tokenize(input2)).thenReturn(List.of());

        String input3 = "";
        when(textTokenizer.tokenize(input3)).thenReturn(List.of());

        String inputOutsideBook = "academy";
        when(textTokenizer.tokenize(inputOutsideBook)).thenReturn(List.of("academy"));

        Map<String, Double> expected = Map.of(
            "academy", 0.0
        );

        assertEquals(expected, tfidfSimilarityCalculator.computeIDF(outsideBook), "It should return 0.0 when books do not include this word.");
    }

    @Test
    void testMultiplyValues() {
        Map<String, Double> tf = Map.of(
            "academy", 0.5
        );
        Map<String, Double> idf = Map.of(
            "academy", 0.17
        );

        Map<String, Double> expected = Map.of(
            "academy", 0.085
        );

        assertEquals(expected, tfidfSimilarityCalculator.multiplyValues(tf, idf), "Should calculate valid weight.");
    }
}
