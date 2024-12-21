package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicSymbolsConstants.EMPTY_STRING;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicSymbolsConstants.PUNCT_REGEX;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicSymbolsConstants.SPACE;
import static bg.sofia.uni.fmi.mjt.goodreads.constants.MagicSymbolsConstants.SPACES_REGEX;

public class TextTokenizer {

    private final Set<String> stopwords;

    public TextTokenizer(Reader stopwordsReader) {
        try (var br = new BufferedReader(stopwordsReader)) {
            stopwords = br.lines().collect(Collectors.toSet());
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not load dataset", ex);
        }
    }

    public List<String> tokenize(String input) {
        if (input == null || input.isEmpty()) {
            return List.of();
        }

        return Stream.of(input.toLowerCase()
                .replaceAll(PUNCT_REGEX, EMPTY_STRING)
                .replaceAll(SPACES_REGEX, SPACE)
                .trim()
                .split(SPACE))
            .filter(token -> !token.isEmpty())
            .filter(token -> !stopwords.contains(token))
            .toList();
    }

    public Set<String> stopwords() {
        return stopwords;
    }

}