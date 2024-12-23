package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.goodreads.constants.ErrorMessagesConstants.ARGUMENT_NULL_ERROR;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.ErrorMessagesConstants.INVALID_NULL_BOOKS;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.FIRST_COLUMN;

public class BookRecommender implements BookRecommenderAPI {
    Set<Book> initialBooks;
    SimilarityCalculator calculator;

    public BookRecommender(Set<Book> initialBooks, SimilarityCalculator calculator) {
        this.initialBooks = initialBooks;
        this.calculator = calculator;
    }

    @Override
    public SortedMap<Book, Double> recommendBooks(Book origin, int maxN) {
        if (origin == null) {
            throw new IllegalArgumentException(INVALID_NULL_BOOKS);
        }
        if (maxN <= FIRST_COLUMN) {
            throw new IllegalArgumentException(ARGUMENT_NULL_ERROR);
        }

        Map<Book, Double> similarityScores = new HashMap<>();

        for (Book book : initialBooks) {
            if (!book.equals(origin)) {
                double similarity = calculator.calculateSimilarity(origin, book);
                similarityScores.put(book, similarity);
            }
        }

        return similarityScores.entrySet().stream()
            .sorted(Map.Entry.<Book, Double>comparingByValue(Comparator.reverseOrder())
                .thenComparing(entry -> entry.getKey().ID()))
            .limit(maxN)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                () -> new TreeMap<>(Comparator.comparing(similarityScores::get).reversed())
            ));
    }

}