package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static bg.sofia.uni.fmi.mjt.goodreads.constants.ErrorMessagesConstants.INVALID_NULL_BOOKS;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.FIRST_COLUMN;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.ZERO_DBL;

public class GenresOverlapSimilarityCalculator implements SimilarityCalculator {

    @Override
    public double calculateSimilarity(Book first, Book second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException(INVALID_NULL_BOOKS);
        }

        List<String> firstGenres = first.genres();
        List<String> secondGenres = second.genres();

        Set<String> secondGenresSet = new HashSet<>(secondGenres);

        long intersectionCount = firstGenres.stream()
            .filter(secondGenresSet::contains)
            .count();

        int minLen = Math.min(firstGenres.size(), secondGenres.size());

        if (minLen == FIRST_COLUMN) {
            return ZERO_DBL;
        }

        return (double) intersectionCount / minLen;
    }

}