package bg.sofia.uni.fmi.mjt.goodreads.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static bg.sofia.uni.fmi.mjt.goodreads.constants.ErrorMessagesConstants.INVALID_TOKENS_LEN;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.EIGHTH_COLUMN;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.FIFTH_COLUMN;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.FIRST_COLUMN;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.FOURTH_COLUMN;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.SECOND_COLUMN;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.SEVENTH_COLUMN;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.SIXTH_COLUMN;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.SIZE_OF_COLUMNS;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicNumbersConstants.THIRD_COLUMN;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicSymbolsConstants.COMMA;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicSymbolsConstants.COMMA_SPACE;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicSymbolsConstants.EMPTY_STRING;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicSymbolsConstants.LEFT_BRACKET;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicSymbolsConstants.RIGHT_BRACKET;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicSymbolsConstants.SINGLE_QUOTE;

public record Book(
    String ID,
    String title,
    String author,
    String description,
    List<String> genres,
    double rating,
    int ratingCount,
    String URL
) {

    public static Book of(String[] tokens) {
        if (tokens.length != SIZE_OF_COLUMNS) {
            throw new IllegalArgumentException(INVALID_TOKENS_LEN);
        }

        return new Book(
            tokens[FIRST_COLUMN],
            tokens[SECOND_COLUMN],
            tokens[THIRD_COLUMN],
            tokens[FOURTH_COLUMN],
            splitGenres(tokens[FIFTH_COLUMN]),
            Double.parseDouble(tokens[SIXTH_COLUMN]),
            Integer.parseInt(tokens[SEVENTH_COLUMN].replace(COMMA, EMPTY_STRING)),
            tokens[EIGHTH_COLUMN]
        );
    }

    private static List<String> splitGenres(String input) {
        String[] elements = input.replace(LEFT_BRACKET, EMPTY_STRING)
            .replace(RIGHT_BRACKET, EMPTY_STRING)
            .replace(SINGLE_QUOTE, EMPTY_STRING)
            .split(COMMA_SPACE);

        return new ArrayList<>(Arrays.asList(elements));
    }
}