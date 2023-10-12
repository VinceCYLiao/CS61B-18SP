package byog.Core;

import byog.TileEngine.TETile;

import java.util.Random;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
//        int seed = 12342124;
//        if (args.length > 1) {
//            System.out.println("Can only have one argument - the input string");
//            System.exit(0);
//        } else if (args.length == 1) {
//            Game game = new Game(seed);
//            TETile[][] worldState = game.playWithInputString(args[0]);
//            System.out.println(TETile.toString(worldState));
//        } else {
//            Game game = new Game(seed);
//            game.playWithKeyboard();
//        }
        Game game = new Game(new Random().nextInt());
        game.test();
    }
}
