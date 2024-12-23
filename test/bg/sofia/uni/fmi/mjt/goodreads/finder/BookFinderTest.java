package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class BookFinderTest {
    Book book1 = Mockito.mock();
    Book book2 = Mockito.mock();
    TextTokenizer textTokenizer = Mockito.mock();

    BookFinder finder = new BookFinder(Set.of(book1, book2), textTokenizer);

    @Test
    void testAllGenres() {
        when(book1.genres()).thenReturn(List.of("Novel"));
        when(book2.genres()).thenReturn(List.of("Fiction", "Novel"));

        assertEquals(Set.of("Novel", "Fiction"), finder.allGenres(), "Should return all distinct genres.");
    }

    @Test
    void testAllBooks() {
        assertEquals(Set.of(book1, book2), finder.allBooks(), "Should return all distinct books.");
    }

    @Test
    void testSearchByAuthorWithValidAuthor() {
        when(book1.author()).thenReturn("Author A");
        when(book2.author()).thenReturn("Author B");

        List<Book> result = finder.searchByAuthor("Author B");
        assertEquals(List.of(book2), result, "Should return the book with corresponding author.");
    }

    @Test
    void testSearchByAuthorWithMissingAuthor() {
        when(book1.author()).thenReturn("Author A");
        when(book2.author()).thenReturn("Author B");

        List<Book> result = finder.searchByAuthor("Author X");
        assertEquals(List.of(), result, "Should return an empty list when no matching authors.");
    }

    @Test
    void testSearchByAuthorWithNullAuthor() {
        assertThrows(IllegalArgumentException.class, () -> finder.searchByAuthor(null),
            "Should throw an exception when null author.");
    }

    @Test
    void testSearchByAuthorWithBlankAuthor() {
        assertThrows(IllegalArgumentException.class, () -> finder.searchByAuthor("   "),
            "Should throw an exception when empty or blank author. ");
    }

    @Test
    void testSearchByGenresWithNull() {
        assertThrows(IllegalArgumentException.class, () -> finder.searchByGenres(null, MatchOption.MATCH_ALL),
            "Should throw an exception when genres is null. ");
    }

    @Test
    void testSearchByKeywordsWithNull() {
        assertThrows(IllegalArgumentException.class, () -> finder.searchByKeywords(null, MatchOption.MATCH_ALL),
            "Should throw an exception when keywords is null. ");
    }

    @Test
    void testSearchByGenresWithMatchAny() {
        when(book1.genres()).thenReturn(List.of("Genre A", "Genre B"));
        when(book2.genres()).thenReturn(List.of("Genre B", "Genre C"));
        when(book1.ID()).thenReturn("A");
        when(book2.ID()).thenReturn("B");

        Set<String> genres = Set.of("Genre B");
        List<Book> result = new ArrayList<>(finder.searchByGenres(genres, MatchOption.MATCH_ANY));
        result.sort(Comparator.comparing(Book::ID));

        List<Book> expected = new ArrayList<>(List.of(book1, book2));
        expected.sort(Comparator.comparing(Book::ID));

        assertEquals(expected, result, "Should return all books that match any of the genres.");
    }

    @Test
    void testSearchByGenresWithMatchAnyOneBook() {
        when(book1.genres()).thenReturn(List.of("Genre A", "Genre B"));
        when(book2.genres()).thenReturn(List.of("Genre F", "Genre C"));

        Set<String> genres = Set.of("Genre B", "Genre E", "Genre D");
        List<Book> result = finder.searchByGenres(genres, MatchOption.MATCH_ANY);

        assertEquals(List.of(book1), result, "Should return all books that match any of the genres.");
    }

    @Test
    void testSearchByGenresWithMatchAnyAllBooks() {
        when(book1.genres()).thenReturn(List.of("Genre A", "Genre B"));
        when(book2.genres()).thenReturn(List.of("Genre F", "Genre C"));
        when(book1.ID()).thenReturn("A");
        when(book2.ID()).thenReturn("B");

        Set<String> genres = Set.of("Genre B", "Genre F", "Genre D");
        List<Book> result = new ArrayList<>(finder.searchByGenres(genres, MatchOption.MATCH_ANY));
        result.sort(Comparator.comparing(Book::ID));

        List<Book> expected = new ArrayList<>(List.of(book1, book2));
        expected.sort(Comparator.comparing(Book::ID));

        assertEquals(expected, result, "Should return all books that match any of the genres.");
    }

    @Test
    void testSearchByGenresWithMatchAnyNoBooks() {
        when(book1.genres()).thenReturn(List.of("Genre A", "Genre B"));
        when(book2.genres()).thenReturn(List.of("Genre F", "Genre C"));

        Set<String> genres = Set.of("Genre BD", "Genre FF", "Genre FD");
        List<Book> result = finder.searchByGenres(genres, MatchOption.MATCH_ANY);
        assertEquals(List.of(), result, "Should return empty list when no books match.");
    }

    @Test
    void testSearchByGenresWithMatchAll() {
        when(book1.genres()).thenReturn(List.of("Genre A", "Genre B", "Genre C"));
        when(book2.genres()).thenReturn(List.of("Genre F", "Genre C"));

        Set<String> genres = Set.of("Genre A", "Genre B", "Genre C");
        List<Book> result = finder.searchByGenres(genres, MatchOption.MATCH_ALL);
        assertEquals(List.of(book1), result, "Should return all books that match all of the genres.");
    }

    @Test
    void testSearchByKeywordsWithMatchAll() {
        when(book1.description()).thenReturn("love romance");
        when(book1.title()).thenReturn("Title1");
        when(book2.description()).thenReturn("adventure");
        when(book2.title()).thenReturn("Title2");

        when(textTokenizer.tokenize("love romance")).thenReturn(List.of("love", "romance"));
        when(textTokenizer.tokenize("Title1")).thenReturn(List.of("title1"));
        when(textTokenizer.tokenize("adventure")).thenReturn(List.of("adventure"));
        when(textTokenizer.tokenize("Title2")).thenReturn(List.of("title2"));

        Set<String> keywords = Set.of("romance", "love");
        List<Book> result = finder.searchByKeywords(keywords, MatchOption.MATCH_ALL);
        assertEquals(List.of(book1), result);
    }

    @Test
    void testSearchByKeywordsWithMatchAllNoResults() {
        when(book1.description()).thenReturn("love romance");
        when(book1.title()).thenReturn("Title1");
        when(book2.description()).thenReturn("adventure");
        when(book2.title()).thenReturn("Title2");

        when(textTokenizer.tokenize("love romance")).thenReturn(List.of("love", "romance"));
        when(textTokenizer.tokenize("Title1")).thenReturn(List.of("title1"));
        when(textTokenizer.tokenize("adventure")).thenReturn(List.of("adventure"));
        when(textTokenizer.tokenize("Title2")).thenReturn(List.of("title2"));

        Set<String> keywords = Set.of("romance", "love", "adventure");
        List<Book> result = finder.searchByKeywords(keywords, MatchOption.MATCH_ALL);
        assertEquals(List.of(), result);
    }

    @Test
    void testSearchByKeywordsWithMatchAllTitle() {
        when(book1.description()).thenReturn("love romance");
        when(book1.title()).thenReturn("Title1");
        when(book2.description()).thenReturn("adventure");
        when(book2.title()).thenReturn("Title2");

        when(textTokenizer.tokenize("love romance")).thenReturn(List.of("love", "romance"));
        when(textTokenizer.tokenize("Title1")).thenReturn(List.of("title1"));
        when(textTokenizer.tokenize("adventure")).thenReturn(List.of("adventure"));
        when(textTokenizer.tokenize("Title2")).thenReturn(List.of("title2"));

        Set<String> keywords = Set.of("tItle1");
        List<Book> result = finder.searchByKeywords(keywords, MatchOption.MATCH_ALL);
        assertEquals(List.of(book1), result);
    }
}