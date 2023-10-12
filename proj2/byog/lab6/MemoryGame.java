package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("Please enter a seed");
//            return;
//        }

//        int seed = Integer.parseInt(args[0]);
        int seed = 1231421;
        MemoryGame game = new MemoryGame(100, 60, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.rand = new Random(seed);
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        StringBuilder str = new StringBuilder();
        while(n != 0) {
           str.append(CHARACTERS[this.rand.nextInt(25)]);
           n--;
        }
        return str.toString();
    }

    public void drawUI(String action, String encouragement) {
        StdDraw.text(6, this.height - 2, "Round:" + this.round);
        StdDraw.text(this.width/2.0, this.height - 2, action);
        StdDraw.text(this.width -  encouragement.length(), this.height - 2, encouragement);
        StdDraw.line(0, this.height - 3, this.width, this.height - 3);
        StdDraw.show();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear();
        StdDraw.text(this.width/2.0, this.height/2.0, s);
        StdDraw.show();
    }

    private void displayBlankScreen() {
        StdDraw.clear();
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        char[] charArray = letters.toCharArray();
        String encouragement = ENCOURAGEMENT[this.rand.nextInt(ENCOURAGEMENT.length)];
        for(char c: charArray) {

           drawFrame(String.valueOf(c));
            drawUI("Watch!", encouragement);
            StdDraw.pause(1000);
            displayBlankScreen();
            drawUI("Type!", encouragement);
            StdDraw.pause(500);

        }
    }

    public String solicitNCharsInput(int n) {
        StringBuilder str = new StringBuilder();
        while (StdDraw.hasNextKeyTyped() && n > 0) {
            str.append(StdDraw.nextKeyTyped());
            n--;
        }
        return str.toString();
    }

    public void startGame() {
        while(true) {
            this.round += 1;
            this.drawFrame("Round:" + this.round);
            StdDraw.pause(2000);
            String randomStr = this.generateRandomString(this.round);
            this.flashSequence(randomStr);
            String answer = this.solicitNCharsInput(this.round);
            if(!answer.equals(randomStr)) {
                break;
            }
        }
        this.drawFrame("Game Over! You made it to round:" + this.round);
    }

}
