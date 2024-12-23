package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.goodreads.constants.ErrorMessagesConstants.INVALID_NULL_BOOKS;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.FIRST_COLUMN;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.SECOND_COLUMN;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.ZERO_LONG;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.ZERO_DBL;

public class TFIDFSimilarityCalculator implements SimilarityCalculator {
    private Set<Book> books;
    private TextTokenizer tokenizer;

    public TFIDFSimilarityCalculator(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    /*
     * Do not modify!
     */
    @Override
    public double calculateSimilarity(Book first, Book second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException(INVALID_NULL_BOOKS);
        }

        Map<String, Double> tfIdfScoresFirst = computeTFIDF(first);
        Map<String, Double> tfIdfScoresSecond = computeTFIDF(second);

        return cosineSimilarity(tfIdfScoresFirst, tfIdfScoresSecond);
    }

    public Map<String, Double> computeTFIDF(Book book) {
        Map<String, Double> resTF = computeTF(book);
        Map<String, Double> resIDF = computeIDF(book);

        return multiplyValues(resTF, resIDF);
    }

    public Map<String, Double> multiplyValues(Map<String, Double> map1, Map<String, Double> map2) {
        return map1.keySet().stream()
            .collect(Collectors.toMap(
                key -> key,
                key -> map1.get(key) * map2.get(key)
            ));
    }

    public Map<String, Double> computeTF(Book book) {
        Map<String, Long> words = getWords(book);
        long wordsCount = getWordsCount(book);

        return words.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> wordsCount == FIRST_COLUMN ? ZERO_DBL : (double) entry.getValue() / wordsCount
            ));
    }

    public Map<String, Double> computeIDF(Book book) {
        Map<String, Double> idfMap = new HashMap<>();
        List<String> allWords = tokenizer.tokenize(book.description());
        long booksCount = books.size();

        Map<String, Long> wordBookCount = new HashMap<>();
        for (Book cur : books) {
            Set<String> uniqueWords = new HashSet<>(tokenizer.tokenize(cur.description()));
            for (String word : uniqueWords) {
                wordBookCount.put(word, wordBookCount.getOrDefault(word, ZERO_LONG) + SECOND_COLUMN);
            }
        }

        for (String word : allWords) {
            long booksWithSameWord = wordBookCount.getOrDefault(word, ZERO_LONG);
            double idf = (booksWithSameWord != ZERO_DBL) ? Math.log10((double) booksCount / booksWithSameWord) :
                ZERO_DBL;
            idfMap.put(word, idf);
        }

        return idfMap;
    }

    private Map<String, Long> getWords(Book book) {
        List<String> allWords = tokenizer.tokenize(book.description());

        return allWords.stream()
            .collect(Collectors.groupingBy(
                word -> word,
                Collectors.counting()
            ));
    }

    private long getWordsCount(Book book) {
        return tokenizer.tokenize(book.description()).size();
    }

    private double cosineSimilarity(Map<String, Double> first, Map<String, Double> second) {
        double magnitudeFirst = magnitude(first.values());
        double magnitudeSecond = magnitude(second.values());

        return dotProduct(first, second) / (magnitudeFirst * magnitudeSecond);
    }

    private double dotProduct(Map<String, Double> first, Map<String, Double> second) {
        Set<String> commonKeys = new HashSet<>(first.keySet());
        commonKeys.retainAll(second.keySet());

        return commonKeys.stream()
            .mapToDouble(word -> first.get(word) * second.get(word))
            .sum();
    }

    private double magnitude(Collection<Double> input) {
        double squaredMagnitude = input.stream()
            .map(v -> v * v)
            .reduce(ZERO_DBL, Double::sum);

        return Math.sqrt(squaredMagnitude);
    }
}