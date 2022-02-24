import org.eclipse.collections.api.bag.primitive.CharBag;
import org.eclipse.collections.api.bag.primitive.MutableCharBag;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.primitive.CharSet;
import org.eclipse.collections.api.tuple.primitive.CharIntPair;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.factory.Strings;
import org.eclipse.collections.impl.factory.primitive.CharSets;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.eclipse.collections.impl.utility.StringIterate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HaikuTest {
    private final String haiku = """
            Breaking Through                  Pavement                  Wakin' with Bacon        Homeward Found
            ----------------                  --------                  -----------------        --------------
            The wall disappears               Beautiful pavement!       Wakin' with Bacon        House is where I am
            As soon as you break through the  Imperfect path before me  On a Saturday morning    Home is where I want to be
            Intimidation                      Thank you for the ride    Life’s little pleasures  Both may be the same
                        
            Winter Slip and Slide              Simple Nothings                With Deepest Regrets
            ---------------------              ---------------                --------------------
            Run up the ladder                  A simple flower                With deepest regrets
            Swoosh down the slide in the snow  Petals shine vibrant and pure  That which you have yet to write
            Winter slip and slide              Stares into the void           At death, won't be wrote
                        
            Caffeinated Coding Rituals  Finding Solace               Curious Cat                Eleven
            --------------------------  --------------               -----------                ------
            I arrange my desk,          Floating marshmallows        I see something move       This is how many
            refactor some ugly code,    Cocoa brewed hot underneath  What it is, I am not sure  Haiku I write before I
            and drink my coffee.        Comfort in a cup             Should I pounce or not?    Write a new tech blog.
            """;

    /***
     *
     * finding 3 most frequent letter from the text
     *
     */
    @Test
    public void topLettersEclipseCollections() {

        CharBag chars = Strings.asChars(this.haiku).select(Character::isAlphabetic).collectChar(Character::toLowerCase).toBag();

        ListIterable<CharIntPair> top3 = chars.topOccurrences(3);

        Assertions.assertEquals(PrimitiveTuples.pair('e', 94), top3.get(0));
        Assertions.assertEquals(PrimitiveTuples.pair('t', 65), top3.get(1));
        Assertions.assertEquals(PrimitiveTuples.pair('i', 62), top3.get(2));
    }

    /**
     * finding 3 most frequent letter from the text
     */
    @Test
    public void topLettersJavaStream() {
        Map<Character, Long> chars = this.haiku.chars()
                .filter(Character::isAlphabetic)
                .map(Character::toLowerCase)
                .mapToObj(i -> (char) i)
                .collect(
                        Collectors.groupingBy(Function.identity(),
                                Collectors.counting()));

        System.out.println(chars);

        List<Map.Entry<Character, Long>> top3 = chars.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .toList();

        System.out.println("-------");

        System.out.println(top3);

        Assertions.assertEquals(new AbstractMap.SimpleEntry<>('e', 94L), top3.get(0));
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>('t', 65L), top3.get(1));
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>('i', 62L), top3.get(2));
    }

    /**
     * 1
     * find all letter in text bloc
     */
    @Test
    public void distinctLettersEclipseCollections() {
        String distinctLetters = Strings.asChars(this.haiku).select(Character::isAlphabetic).collectChar(Character::toLowerCase).distinct().toString();

        Assertions.assertEquals("breakingthoupvmwcdflsy", distinctLetters);
    }

    /*
    1
     */
    @Test
    public void distinctLettersJavaStreams() {
        //create Stream of all characters
        String distinctLetters = this.haiku.chars()
                .filter(Character::isAlphabetic)
                .map(Character::toLowerCase).distinct()
                //.mapToObj(i -> (char) i)
                .mapToObj(Character::toString)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();

        Assertions.assertEquals("breakingthoupvmwcdflsy", distinctLetters);
    }

    @Test
    public void duplicatesAndUniqueEclipseCollections() {
        MutableCharBag chars = Strings.asChars(this.haiku).select(Character::isAlphabetic).collectChar(Character::toLowerCase).toBag();

        CharBag duplicates = chars.selectDuplicates();
        CharSet unique = chars.selectUnique();

        Assertions.assertEquals(chars, duplicates);
        Assertions.assertEquals(CharSets.immutable.empty(), unique);
    }

    @Test
    public void duplicatesAndUniqueJavaStreams() {
        Map<Character, Long> chars = this.haiku.chars().filter(Character::isAlphabetic).map(Character::toLowerCase).mapToObj(i -> (char) i).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<Character, Long> duplicates = chars.entrySet().stream().filter(entry -> entry.getValue() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Set<Character> unique = chars.entrySet().stream().filter(entry -> entry.getValue() < 2).map(Map.Entry::getKey).collect(Collectors.toSet());

        Assertions.assertEquals(chars, duplicates);
        Assertions.assertEquals(Set.of(), unique);
    }

    @Test
    public void topVowelAndConsonantEclipseCollections() {
        MutableList<CharIntPair> charIntPairs = Strings.asChars(this.haiku).asLazy().select(Character::isAlphabetic).collectChar(Character::toLowerCase).toBag().topOccurrences(26);

        char topVowel = charIntPairs.detect(pair -> this.isVowel(pair.getOne())).getOne();
        char topConsonant = charIntPairs.detect(pair -> !this.isVowel(pair.getOne())).getOne();

        Assertions.assertEquals('e', topVowel);
        Assertions.assertEquals('t', topConsonant);
    }

    @Test
    public void topVowelAndConsonantJavaStreams() {
        List<Map.Entry<Character, Long>> entries = this.haiku.chars().filter(Character::isAlphabetic).map(Character::toLowerCase).mapToObj(i -> (char) i).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).toList();

        char topVowel = entries.stream().filter(e -> this.isVowel(e.getKey())).findFirst().orElseThrow().getKey();

        char topConsonant = entries.stream().filter(e -> !this.isVowel(e.getKey())).findFirst().orElseThrow().getKey();

        Assertions.assertEquals('e', topVowel);
        Assertions.assertEquals('t', topConsonant);
    }

    public boolean isVowel(char character) {
        return switch (character) {
            case 'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U' -> true;
            default -> false;
        };
    }

    @Test
    public void haikuWordWordsEclipseCollections() {
        MutableList<String> words = Lists.mutable.empty();
        StringIterate.forEachToken(this.haiku, " ,.-!?\t\n\r\f", words::add);
        Assertions.assertEquals(168, words.size());
        Set<String> wordleWords = words.asLazy().reject(word -> word.contains("'")).select(word -> word.length() == 5).collect(String::toLowerCase).toSet();
        ImmutableSet<String> expected = Sets.immutable.with("haiku", "death", "wrote", "bacon", "shine", "house", "where", "thank", "break", "which", "cocoa", "drink", "write", "slide", "found");
        Assertions.assertEquals(expected, wordleWords);
    }

    @Test
    public void haikuWordWordsJavaStreams() {
        List<String> words = new Scanner(this.haiku).useDelimiter("[\\s,.!?-]+").tokens().toList();
        Assertions.assertEquals(168, words.size());
        Set<String> wordleWords = words.stream().filter(word -> !word.contains("'")).filter(word -> word.length() == 5).map(String::toLowerCase).collect(Collectors.toSet());
        Set<String> expected = Set.of("haiku", "death", "wrote", "bacon", "shine", "house", "where", "thank", "break", "which", "cocoa", "drink", "write", "slide", "found");
        Assertions.assertEquals(expected, wordleWords);
    }


    @Test
    public void haikuWordWordsJava17() {
        record Word(String word) {
            Word(String word) {
                this.word = word.toLowerCase();
            }

            public boolean containsFiveLettersAndIsntContraction() {
                return this.word.length() == 5 && !this.word.contains("'");
            }
        }

        List<String> words = Pattern.compile("[\\s,.!?-]+").splitAsStream(this.haiku).toList();
        Assertions.assertEquals(168, words.size());
        Set<Word> wordleWords = words.stream().map(Word::new).filter(Word::containsFiveLettersAndIsntContraction).collect(Collectors.toSet());
        Set<Word> expected = Stream.of("haiku", "death", "wrote", "bacon", "shine", "house", "where", "thank", "break", "which", "cocoa", "drink", "write", "slide", "found").map(Word::new).collect(Collectors.toSet());
        Assertions.assertEquals(expected, wordleWords);
    }

    @Test
    public void haikuWordleWordsJava17EclipseCollections() {
        var wordList = Pattern.compile("[\\s,.!?-]+").splitAsStream(this.haiku).collect(Collectors2.collect(String::toLowerCase, Lists.mutable::empty));
        //  Verify.assertSize(168, wordList);

        var wordleWordsSet = wordList.select(word -> word.length() == 5 && !word.contains("'"), Sets.mutable.empty());
        var expectedSet = Sets.immutable.with("haiku", "death", "wrote", "bacon", "shine", "house", "where", "thank", "break", "which", "cocoa", "drink", "write", "slide", "found");

        Assertions.assertEquals(expectedSet, wordleWordsSet);
    }

    /**
     * Hassen Ben Slima
     * Better  readability make your code easy to maintain
     */

    /*
    1-
   */
    @Test
    public void distinctLettersJavaStreams_17() {
        //create Stream of all characters
        String distinctLetters = this.haiku.chars()
                .filter(Character::isAlphabetic)
                .map(Character::toLowerCase).distinct()
                .mapToObj(Character::toString)
                .collect(Collectors.joining());

        System.out.println("distinctLetters = " + distinctLetters);
        Assertions.assertEquals("breakingthoupvmwcdflsy", distinctLetters);


    }


    /*
     2-
    finding the 3 most frequency kata from the text
  */
    @Test
    public void top_letters_JS_V1() {
        Map<String, Long> map = this.haiku.chars()
                .filter(Character::isAlphabetic)
                .map(Character::toLowerCase)
                .mapToObj(Character::toString)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
        System.out.println("map = " + map);
        Map.Entry<String, Long> mostFrequentLetter = map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();
        System.out.println("mostFrequentLetter = " + mostFrequentLetter);
        List<Map.Entry<String, Long>> mostFrequentLetters = map.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .toList();

        System.out.println("mostFrequentLetters = " + mostFrequentLetters);
    }


    /*
   3-
 */
    @Test
    public void mostSeenLetter() {
        Map<String, Long> chars = this.haiku.chars()
                .filter(Character::isAlphabetic)
                .map(Character::toLowerCase)
                .mapToObj(Character::toString)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        Map<Long, List<String>> map = chars.entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        Collectors.mapping(entry -> entry.getKey(),
                                Collectors.toList())
                ));

        Map.Entry<Long, List<String>> mostSeenLetter = map.entrySet().stream().max(Map.Entry.comparingByKey())
                .orElseThrow();

        System.out.println("mostSeenLetter = " + mostSeenLetter);

        List<Map.Entry<Long, List<String>>> mostSeenLetters = map.entrySet().stream().sorted(Map.Entry.comparingByKey(
                        Comparator.reverseOrder()
                ))
                .toList();

        System.out.println("mostSeenLetters = " + mostSeenLetters);


    }



    /*
    4-
     */

    record Letter(int codePoint) {
        //canonical constructor
        Letter(int codePoint) {
            if (!Character.isAlphabetic(codePoint)) {
                throw new IllegalArgumentException("Letter is build ob letters");
            }
            this.codePoint = Character.toLowerCase(codePoint);
        }
    }

    record LetterCount(long count) implements Comparable<LetterCount> {

        @Override
        public int compareTo(LetterCount other) {
            return Long.compare(this.count, other.count);
        }

        static Collector<Object, Object, LetterCount> counting() {
            return Collectors.collectingAndThen(
                    Collectors.counting(),
                    LetterCount::new
            );
        }
    }

    record LetterByCount(Letter letter, LetterCount count) {
        //contractor call the canonical constructor
        LetterByCount(Map.Entry<Letter, LetterCount> entry) {
            this(entry.getKey(), entry.getValue());
        }

        public boolean isNotUnique() {
            return count.count>1;
        }

        public boolean isUnique() {
            return count.count<2;
        }
    }

    public record LettersByCount(LetterCount count, List<Letter> letters) {
        LettersByCount(Map.Entry<LetterCount, List<Letter>> entry) {
            this(entry.getKey(), entry.getValue());
        }

        public static Comparator<? super LettersByCount> comparingByCount() {

            return Comparator.comparing(LettersByCount::count);
        }
    }


    /*
    4-
     */
    @Test
    public void testRecord() {
        Map<Letter, LetterCount> chars = this.haiku.chars()
                .filter(Character::isAlphabetic)
                .mapToObj(Letter::new)
                .collect(
                        Collectors.groupingBy(Function.identity(),
                                Collectors.collectingAndThen(
                                        Collectors.counting(),
                                        LetterCount::new
                                )
                        ));

        System.out.println("chars = " + chars);

        Map<LetterCount, List<Letter>> map0 = chars.entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey,
                                Collectors.toList())
                ));

        System.out.println("map0 = " + map0);

        Map<LetterCount, List<Letter>> map = chars.entrySet().stream()
                .map(LetterByCount::new)
                .collect(Collectors.groupingBy(
                        LetterByCount::count,
                        Collectors.mapping(LetterByCount::letter,
                                Collectors.toList())
                ));

        System.out.println("map = " + map);

        LettersByCount mostSeenLetter = map.entrySet().stream()
                .map(LettersByCount::new)
                .max(LettersByCount.comparingByCount())
                .orElseThrow();

        System.out.println("mostSeenLetter = " + mostSeenLetter);

        List<LettersByCount> mostSeenLetters = map.entrySet().stream()
                .map(LettersByCount::new)
                .sorted(LettersByCount.comparingByCount().reversed())
                .toList();

        System.out.println("mostSeenLetters = " + mostSeenLetters);
    }


    @Test
    public void duplicatesAndUniqueJavaStreams01() {
        Map<Character, Long> lettersByNumber = this.haiku.chars().
                filter(Character::isAlphabetic)
                .map(Character::toLowerCase)
                .mapToObj(i -> (char) i)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<Character, Long> duplicates = lettersByNumber.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Set<Character> unique = lettersByNumber.entrySet().stream()
                .filter(entry -> entry.getValue() < 2)
                .map(Map.Entry::getKey).collect(Collectors.toSet());

        Assertions.assertEquals(lettersByNumber, duplicates);
        Assertions.assertEquals(Set.of(), unique);
    }


    @Test
    public void duplicatesAndUniqueJavaStreams00() {
        Map<Letter, LetterCount> lettersByNumber = this.haiku.chars()
                .mapToObj(Letter::new).
                collect(Collectors.groupingBy(
                        Function.identity(),
                        LetterCount.counting()));

        Set<Letter> duplicates = lettersByNumber.entrySet().stream()
                .map(LetterByCount::new)
                .filter(LetterByCount::isNotUnique)
                .map(LetterByCount::letter)
                .collect(Collectors.toSet());

        Collection<Letter> unique = lettersByNumber.entrySet().stream()
                .map(LetterByCount::new)
                .filter(LetterByCount::isUnique)
                .map(LetterByCount::letter)
                .collect(Collectors.toSet());

        List.of(duplicates, unique);


        Map<Boolean, List<Letter>> partition = lettersByNumber.entrySet().stream()
                .map(LetterByCount::new)
                .collect(Collectors.partitioningBy(
                        LetterByCount::isUnique,
                        Collectors.mapping(
                                LetterByCount::letter,
                                Collectors.toList()
                        )
                ));

        List<Letter> duplicates0 = partition.get(false);
        List<Letter> unique0 = partition.get(true);

        List.of(duplicates0, unique0);
        Assertions.assertEquals(lettersByNumber, duplicates);
        Assertions.assertEquals(Set.of(), unique);
    }

}