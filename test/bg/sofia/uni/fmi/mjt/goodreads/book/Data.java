package bg.sofia.uni.fmi.mjt.goodreads.book;

import java.util.Arrays;
import java.util.List;

public class Data {
    public static String[] validTokens = {
        "1",
        "Harry Potter and the Philosopher’s Stone (Harry Potter, #1)",
        "J.K. Rowling",
        "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A WIZARD!",
        "['Fantasy', 'Fiction', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']",
        "4.47",
        "9,278,135",
        "https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone"
    };

    public static String[] inValidTokens = {
        "J.K. Rowling",
        "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A WIZARD!",
        "['Fantasy', 'Fiction', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']",
        "4.47",
        "9,278,135",
        "https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone"
    };

    public static String[] emptyGenreTokens = {
        "1",
        "Harry Potter and the Philosopher’s Stone (Harry Potter, #1)",
        "J.K. Rowling",
        "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A WIZARD!",
        "[]",
        "4.47",
        "9,278,135",
        "https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone"
    };

    private static final String ID = "1";
    private static final String title = "Harry Potter and the Philosopher’s Stone (Harry Potter, #1)";
    private static final String author = "J.K. Rowling";
    private static final String description = "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A WIZARD!";
    private static final List<String> genres = Arrays.asList(
        "Fantasy", "Fiction", "Young Adult", "Magic", "Childrens", "Middle Grade", "Classics"
    );
    private static final double rating = 4.47;
    private static final int ratingCount = 9278135;  // Parsing and removing commas manually for rating count
    private static final String URL = "https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone";

    public static Book book = new Book(ID, title, author, description, genres, rating, ratingCount, URL);
    public static Book book2 = new Book(ID, title, author, description, List.of(""), rating, ratingCount, URL);
}
