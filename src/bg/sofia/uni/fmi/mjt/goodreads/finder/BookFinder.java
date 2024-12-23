package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.goodreads.constants.ErrorMessagesConstants.AUTHOR_NAME_ERROR;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.ErrorMessagesConstants.INVALID_GENRES_ERROR;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.ErrorMessagesConstants.INVALID_KEYWORDS_ERROR;

public class BookFinder implements BookFinderAPI {

    Set<Book> books;
    TextTokenizer textTokenizer;

    public BookFinder(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.textTokenizer = tokenizer;
    }

    public Set<Book> allBooks() {
        return books;
    }

    @Override
    public List<Book> searchByAuthor(String authorName) {
        if (authorName == null || authorName.isEmpty() || authorName.isBlank()) {
            throw new IllegalArgumentException(AUTHOR_NAME_ERROR);
        }

        return books.stream()
            .filter(book -> book.author().equals(authorName))
            .toList();
    }

    @Override
    public Set<String> allGenres() {
        return books.stream()
            .flatMap(book -> book.genres().stream())
            .collect(Collectors.toSet());
    }

    @Override
    public List<Book> searchByGenres(Set<String> genres, MatchOption option) {
        if (genres == null) {
            throw new IllegalArgumentException(INVALID_GENRES_ERROR);
        }

        return books.stream()
            .filter(book -> matchesGenres(book, genres, option))
            .toList();
    }

    private boolean matchesGenres(Book book, Set<String> genres, MatchOption option) {
        return switch (option) {
            case MATCH_ANY -> book.genres().stream().anyMatch(genres::contains);
            case MATCH_ALL -> new HashSet<>(book.genres()).containsAll(genres);
        };
    }

    @Override
    public List<Book> searchByKeywords(Set<String> keywords, MatchOption option) {
        if (keywords == null) {
            throw new IllegalArgumentException(INVALID_KEYWORDS_ERROR);
        }

        return books.stream()
            .filter(book -> matchesKeywords(book, keywords, option))
            .toList();
    }

    private boolean matchesKeywords(Book book, Set<String> keywords, MatchOption option) {
        List<String> tokens = new ArrayList<>(textTokenizer.tokenize(book.description()));
        tokens.addAll(textTokenizer.tokenize(book.title()));

        Set<String> lowerCaseKeywords = keywords.stream()
            .map(String::toLowerCase)
            .collect(Collectors.toSet());

        return switch (option) {
            case MATCH_ANY -> tokens.stream().anyMatch(lowerCaseKeywords::contains);
            case MATCH_ALL -> new HashSet<>(tokens).containsAll(lowerCaseKeywords);
        };
    }

}