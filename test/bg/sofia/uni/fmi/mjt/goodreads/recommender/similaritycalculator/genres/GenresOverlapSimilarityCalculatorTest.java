package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenresOverlapSimilarityCalculatorTest {
    GenresOverlapSimilarityCalculator calculator = new GenresOverlapSimilarityCalculator();

    @Test
    void testCalculateSimilarityWithCommonGenres() {
        Book first = mock(Book.class);
        Book second = mock(Book.class);

        when(first.genres()).thenReturn(List.of("Drama", "Action", "Sci-Fi", "Novel"));
        when(second.genres()).thenReturn(List.of("Action", "Comedy", "Drama"));

        assertEquals(2.0 / 3, calculator.calculateSimilarity(first, second),
            "Should divide two common genres by three total.");
    }

    @Test
    void testCalculateSimilarityWithNoCommonGenres() {
        Book first = mock(Book.class);
        Book second = mock(Book.class);

        when(first.genres()).thenReturn(List.of("Drama", "Action"));
        when(second.genres()).thenReturn(List.of("Sci-Fi"));

        assertEquals(0.0, calculator.calculateSimilarity(first, second), "Should return zero if no common genres.");
    }

    @Test
    void testCalculateSimilarityWithSameGenres() {
        Book first = mock(Book.class);
        Book second = mock(Book.class);

        when(first.genres()).thenReturn(List.of("Drama"));
        when(second.genres()).thenReturn(List.of("Drama"));

        assertEquals(1.0, calculator.calculateSimilarity(first, second), "Should return 1.0 for same genres.");
    }

    @Test
    void testCalculateSimilarityWithNullBook() {
        Book validBook = mock(Book.class);
        when(validBook.genres()).thenReturn(List.of("Drama"));

        assertThrows(IllegalArgumentException.class, () -> calculator.calculateSimilarity(null, validBook));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateSimilarity(validBook, null));
    }

    @Test
    void testCalculateSimilarityWithEmptyGenres() {
        Book validBook = mock(Book.class);
        Book emptyBook = mock(Book.class);

        when(validBook.genres()).thenReturn(List.of("Drama", "Action"));
        when(emptyBook.genres()).thenReturn(List.of());

        assertEquals(0.0, calculator.calculateSimilarity(emptyBook, emptyBook));
        assertEquals(0.0, calculator.calculateSimilarity(validBook, emptyBook));
    }
}