package byog.lab6;

import org.junit.Test;

import org.junit.Assert;

public class MemoryGameTest {
    public MemoryGameTest() {}

    @Test
    public void testGenerateRandomString() {
        MemoryGame game = new MemoryGame(20, 20, 20);
        String s1 = game.generateRandomString(7);
        System.out.println(s1);
        Assert.assertEquals(s1.length(), 7);
    }
}
