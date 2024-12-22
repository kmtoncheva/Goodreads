package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.composite;

import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CompositeSimilarityCalculatorTest {
    SimilarityCalculator calc1 = Mockito.mock();
    SimilarityCalculator calc2 = Mockito.mock();

    Map<SimilarityCalculator, Double> map = Map.of(
        calc1, 0.3,
        calc2, 0.7
    );

    CompositeSimilarityCalculator calculator = new CompositeSimilarityCalculator(map);

    @Test
    void testCalculateSimilarity() {
        when(calc1.calculateSimilarity(null, null)).thenReturn(1.0);
        when(calc2.calculateSimilarity(null, null)).thenReturn(0.5);

        double expected = 0.3 + 0.35;
        assertEquals(expected, calculator.calculateSimilarity(null, null));
    }
}