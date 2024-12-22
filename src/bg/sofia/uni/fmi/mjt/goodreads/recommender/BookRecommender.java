package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Set;
import java.util.SortedMap;

public class BookRecommender implements BookRecommenderAPI {

    public BookRecommender(Set<Book> initialBooks, SimilarityCalculator calculator) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Searches for books that are similar to the provided one.
     *
     * @param origin the book we should calculate similarity with.
     * @param maxN       the maximum number of entries returned
     * @return a SortedMap<Book, Double> representing the top maxN closest books
     * with their similarity to originBook ordered by their similarity score
     * @throws IllegalArgumentException if the originBook is null.
     * @throws IllegalArgumentException if maxN is smaller or equal to 0.
     */
    @Override
    public SortedMap<Book, Double> recommendBooks(Book origin, int maxN) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}