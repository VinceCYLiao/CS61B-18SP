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

        int size = d.size();
        for (int i = 0; i < size / 2; i++) {
            char front = d.removeFirst();
            char back = d.removeLast();
            if (front != back) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = this.wordToDeque(word);
        int size = d.size();
        for (int i = 0; i < size / 2; i++) {
            char front = d.removeFirst();
            char back = d.removeLast();
            if (!cc.equalChars(front, back)) {
                return false;
            }
        }
        return true;
    }
}
