package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.goodreads.constants.ErrorMessagesConstants.AUTHOR_NAME_ERROR;

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
        if(authorName == null || authorName.isEmpty()) {
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

    /**
     * Searches for books that belong to the specified genres.
     * The search can be based on different match options (all or any genres).
     *
     * @param genres a Set of genres to search for.
     * @throws IllegalArgumentException if {@param genres} is null
     * @return a List of books that match the given genres according to the MatchOption
     *         Returns an empty list if no books are found.
     */
    @Override
    public List<Book> searchByGenres(Set<String> genres, MatchOption option) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Searches for books that match the specified keywords.
     * The search can be based on different match options (all or any keywords).
     *
     * @param keywords a {@code Set} of keywords to search for.
     * @param option the {@code MatchOption} that defines the search criteria
     *               (either {@link MatchOption#MATCH_ALL} or {@link MatchOption#MATCH_ANY}).
     * @return a List of books in which the title or description match the given keywords according to the MatchOption
     *         Returns an empty list if no books are found.
     */
    @Override
    public List<Book> searchByKeywords(Set<String> keywords, MatchOption option) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}