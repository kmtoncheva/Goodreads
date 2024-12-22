package bg.sofia.uni.fmi.mjt.goodreads;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.goodreads.constants.ErrorMessagesConstants.LOAD_DATASET_ERROR;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.SECOND_COLUMN;

public class BookLoader {

    public static Set<Book> load(Reader reader) {

        try (CSVReader csvReader = new CSVReader(reader)) {
            return csvReader.readAll().stream()
                .skip(SECOND_COLUMN)
                .map(Book::of)
                .collect(Collectors.toSet());

        } catch (IOException | CsvException ex) {
            throw new IllegalArgumentException(LOAD_DATASET_ERROR, ex);
        }

    }

}