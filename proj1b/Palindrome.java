class Palindrome {

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
        Deque<Character> d = this.wordToDeque(word);

        for (int i = 0; i < d.size(); i++) {
            if (d.size() % 2 != 0 && i == ((d.size() / 2) + 1)) {
                continue;
            }
            if (!cc.equalChars(word.charAt(i), d.removeLast())) {
                return false;
            }
        }
        return true;
    }
}