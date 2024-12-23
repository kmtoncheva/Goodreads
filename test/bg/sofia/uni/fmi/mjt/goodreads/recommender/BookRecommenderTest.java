package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class BookRecommenderTest {
    SimilarityCalculator calculator = Mockito.mock();

    Book book1 = Mockito.mock();
    Book book2 = Mockito.mock();
    Book book3 = Mockito.mock();
    Book book4 = Mockito.mock();

    BookRecommender recommender = new BookRecommender(Set.of(book1, book2, book3, book4), calculator);

    @Test
    void testRecommendBooks() {
        when(calculator.calculateSimilarity(book1, book2)).thenReturn(0.8);
        when(calculator.calculateSimilarity(book1, book3)).thenReturn(0.9);
        when(calculator.calculateSimilarity(book1, book4)).thenReturn(0.7);

        SortedMap<Book, Double> recommendations = recommender.recommendBooks(book1, 2);

        assertEquals(2, recommendations.size(), "Should return exactly 2 recommendations.");
        assertTrue(recommendations.containsKey(book3) && recommendations.containsKey(book2),
            "Recommendations should contain the top 2 most similar books.");
        assertEquals(0.9, recommendations.get(book3), 0.001, "Book3 should have a similarity of 0.9.");
        assertEquals(0.8, recommendations.get(book2), 0.001, "Book2 should have a similarity of 0.8.");

        Iterator<Double> similarityIterator = recommendations.values().iterator();
        double previousValue = similarityIterator.next();
        while (similarityIterator.hasNext()) {
            double currentValue = similarityIterator.next();
            assertTrue(previousValue >= currentValue, "Recommendations should be sorted in descending order of similarity.");
            previousValue = currentValue;
        }
    }

    @Test
    void testRecommendedBooksWithNull() {
        assertThrows(IllegalArgumentException.class, () -> recommender.recommendBooks(null, 9));
    }

    @Test
    void testRecommendedBooksWithInvalidMaxN() {
        assertThrows(IllegalArgumentException.class, () -> recommender.recommendBooks(book1, -9));
    }
}
