import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertFalse(palindrome.isPalindrome("god"));
        assertFalse(palindrome.isPalindrome("dog"));

        CharacterComparator obo = new OffByOne();
        assertFalse(palindrome.isPalindrome("ab2", obo));
        assertTrue(palindrome.isPalindrome("abBCB", obo));
        assertTrue(palindrome.isPalindrome("12332", obo));
        assertFalse(palindrome.isPalindrome("a21da2cax", obo));
        assertFalse(palindrome.isPalindrome("a21,!da2@@!DQW", obo));
    }
}
