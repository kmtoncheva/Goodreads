package bg.sofia.uni.fmi.mjt.goodreads.book;

import org.junit.jupiter.api.Test;

import static bg.sofia.uni.fmi.mjt.goodreads.book.Data.book;
import static bg.sofia.uni.fmi.mjt.goodreads.book.Data.book2;
import static bg.sofia.uni.fmi.mjt.goodreads.book.Data.emptyGenreTokens;
import static bg.sofia.uni.fmi.mjt.goodreads.book.Data.inValidTokens;
import static bg.sofia.uni.fmi.mjt.goodreads.book.Data.validTokens;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookTest {
    @Test
    void testOfWithValidTokens(){
        Book expectedBook = book;
        Book actualBook = Book.of(validTokens); // Book returned from method

        assertEquals(expectedBook, actualBook, "The Book objects should be equal.");
    }

    @Test
    void testOfWithInvalidTokens(){
        assertThrows(IllegalArgumentException.class, () -> Book.of(inValidTokens), "Tokens should have size of eight.");
    }

    @Test
    void testOfWithEmptyGenreTokens(){
        Book expectedBook = book2;
        Book actualBook = Book.of(emptyGenreTokens);

        assertEquals(expectedBook, actualBook, "The Book objects should be equal.");
    }
}
