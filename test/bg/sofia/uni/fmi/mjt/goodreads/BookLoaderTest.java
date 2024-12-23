package bg.sofia.uni.fmi.mjt.goodreads;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookLoaderTest {
    @Test
    void testLoadWithEmptyData() {
        String data = "";
        Reader reader = new StringReader(data);

        Set<Book> books = BookLoader.load(reader);

        assertTrue(books.isEmpty(), "The dataset should be empty when no books are present.");
    }

    @Test
    void testLoadWithInvalidData() {
        String data = """
            ID,Title,Author,Description,Genres,Rating,RatingCount,URL
            1,Book One,Author One,Description One,"['Novel']",4.5,http://example.com/1
            """;

        Reader reader = new StringReader(data);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> BookLoader.load(reader),
            "Should throw an IllegalArgumentException when the data is invalid.");
    }

    @Test
    void testLoadValidData() {
        String csvData = """
            ID,Title,Author,Description,Genres,Rating,RatingCount,URL
            1,Book1,Author One,Description One,"['Novel']",4.5,100,http://example.com/1
            2,Book2,Author Two,Description Two,"['Non-fiction']",4.0,50,http://example.com/2
            """;

        Book book1 = new Book(
            "1",
            "Book1",
            "Author One",
            "Description One",
            List.of("Novel"),
            4.5,
            100,
            "http://example.com/1"
        );

        // Creating the second Book object
        Book book2 = new Book(
            "2",
            "Book2",
            "Author Two",
            "Description Two",
            List.of("Non-fiction"),
            4.0,
            50,
            "http://example.com/2"
        );

        Reader reader = new StringReader(csvData);
        Set<Book> books = BookLoader.load(reader);

        assertEquals(2, books.size(), "The dataset should contain two books.");
        assertTrue(books.contains(book1), "Book One should be in the dataset.");
        assertTrue(books.contains(book2), "Book Two should be in the dataset.");
    }
}
