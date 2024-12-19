import bg.sofia.uni.fmi.mjt.goodreads.BookLoader;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Set<Book> books = BookLoader.load(new BufferedReader(new FileReader("C:\\Users\\Katina\\Desktop\\Java\\Homeworks\\Goodreads\\src\\goodreads_data.csv")));
        Optional<Book> book = books.stream().filter(s -> s.ID().equals("7654")).findAny();
    }
}