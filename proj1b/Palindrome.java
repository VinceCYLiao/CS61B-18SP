import java.util.regex.Pattern;

public class Palindrome {

    public Palindrome() {
    }

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new ArrayDeque<>();
        StringBuilder s = new StringBuilder(word);
        for (int i = 0; i < word.length(); i++) {
            d.addLast(s.charAt(i));
        }
        return d;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> d = this.wordToDeque(word);

        String backward = "";
        while (d.size() != 0) {
            backward += d.removeLast();
        }
        return backward.equals(word);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        word = word.toLowerCase();
        int length = word.length();
        if (length == 0) {
            return true;
        }
        if (!Pattern.matches("\\d+", word) && !Pattern.matches("[a-z]+", word)) {
            return false;
        }
        Deque<Character> d = this.wordToDeque(word);
        for (int i = 0; i < length; i++) {
            if (length % 2 != 0 && i == length / 2) {
                d.removeLast();
                continue;
            }
            if (!cc.equalChars(word.charAt(i), d.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
