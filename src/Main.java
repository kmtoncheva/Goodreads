import bg.sofia.uni.fmi.mjt.goodreads.BookLoader;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.finder.BookFinder;
import bg.sofia.uni.fmi.mjt.goodreads.finder.MatchOption;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

            Reader rdBooks = new BufferedReader(new FileReader("C:\\Users\\Katina\\Desktop\\Java\\Homeworks\\Goodreads\\src\\goodreads_data.csv"));
            Reader stopWords = new BufferedReader(new FileReader("C:\\Users\\Katina\\Desktop\\Java\\Homeworks\\Goodreads\\src\\stopwords.txt"));

            Set<Book> books = BookLoader.load(rdBooks);
            TextTokenizer tokenizer = new TextTokenizer(stopWords);

            BookFinder bookFinder = new BookFinder(books, tokenizer);

            List<Book> match = bookFinder.searchByKeywords(Set.of("Harry", "Potter"), MatchOption.MATCH_ALL);
    }
}